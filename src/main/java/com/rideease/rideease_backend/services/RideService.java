package com.rideease.rideease_backend.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rideease.rideease_backend.common.RideStatus;
import com.rideease.rideease_backend.entity.DriverDetails;
import com.rideease.rideease_backend.entity.Payments;
import com.rideease.rideease_backend.entity.Ride;
import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.exception.RideProcessException;
import com.rideease.rideease_backend.repository.DriverRepository;
import com.rideease.rideease_backend.repository.PaymentsRepository;
import com.rideease.rideease_backend.repository.RideRepository;
import com.rideease.rideease_backend.repository.UserDataRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class RideService {

    private final RideRepository rideRepository;
    private final UserDataRepository userDataRepository;
    private final RabbitMQProducer rabbitMQProducer;
    private final DriverRepository driverRepository;
    private final FareCalculatorImp fareCalculatorImp;
    private final KafkaMessageProducer kafkaMessageProducer;
    private final PaymentsRepository paymentsRepository;

    public RideService(PaymentsRepository paymentsRepository,KafkaMessageProducer kafkaMessageProducer, FareCalculatorImp fareCalculatorImp, DriverRepository driverRepository, RideRepository rideRepository, UserDataRepository userDataRepository, RabbitMQProducer rabbitMQProducer)
    {
        this.paymentsRepository=paymentsRepository;
        this.kafkaMessageProducer = kafkaMessageProducer;
        this.fareCalculatorImp=fareCalculatorImp;
        this.driverRepository=driverRepository;
        this.rideRepository=rideRepository;
        this.userDataRepository=userDataRepository;
        this.rabbitMQProducer=rabbitMQProducer;
    }





    public List<Ride> getMyRide() {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        return rideRepository.findByUserName(userName);
    }

    public Ride getRideById(Integer id){
        return rideRepository.findById(id).orElse(null);
    }


    @Transactional(rollbackOn = Exception.class)
    public Ride bookRide(Ride ride) throws RideProcessException {
        try{
            UserData user = getUserData();
            DriverDetails driverDetails = driverRepository.findDriverByAvailabilityAndHighestRatingAndRideType(ride.getRideType());
            driverDetails.setIsAvailable(false);
            driverRepository.save(driverDetails);
            Payments payments =new Payments();
            setRideBookData(ride, user, driverDetails,payments);
            Ride savedRide = rideRepository.save(ride);
            sendNotification("EMAIL: Ride has been booked successfully",
                    "SMS: Ride has been booked successfully");
            kafkaMessageProducer.sendRideBookEvent("Ride booked: " + savedRide.getId());
            return savedRide;
        } catch (Exception e) {
            throw new RideProcessException("Ride is not booked because not saved in DB",e);
        }

    }

    public Ride completeRide() throws Exception {
        String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Ride> optionalRide = rideRepository.findLatestActiveBookedRide(userName);
        if(optionalRide.isPresent()){
            Ride ride = optionalRide.get();
            DriverDetails driverDetails = ride.getDriverDetails();
            driverDetails.setIsAvailable(true);
            driverRepository.save(driverDetails);
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            setRideCompleteData(ride, driverDetails);
            Ride savedRide = rideRepository.save(ride);
            kafkaMessageProducer.sendRideCompleteEvent(objectMapper.writeValueAsString(savedRide));
            sendNotification("EMAIL: Ride has been Completed successfully",
                    "SMS: Ride has been Completed successfully");
            return savedRide;
        }
        else {
            throw new RideProcessException("Ride is not complete because not saved in DB");
        }
    }

    @Transactional(rollbackOn = Exception.class)
    public Ride cancelRide() throws RideProcessException {
        try{
            String userName  = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Ride> optionalRide = rideRepository.findLatestActiveBookedRide(userName);
            if(optionalRide.isPresent()){
                Ride ride=optionalRide.get();
                DriverDetails driverDetails = ride.getDriverDetails();
                driverDetails.setIsAvailable(true);
                driverRepository.save(driverDetails);
                setRideCancelData(ride,driverDetails);
                Ride savedRide = rideRepository.save(ride);
                sendNotification("EMAIL: Ride has been cancelled successfully",
                        "SMS: Ride has been cancelled successfully");
                return savedRide;
            }
            else{
                throw new RideProcessException("No Active ride for user");
            }
        }
        catch(Exception e)
        {
            throw new RideProcessException("Ride was not cancelled because data not saved in DB",e);
        }
    }



    private void sendNotification(String email, String sms) {
        rabbitMQProducer.sendEmailNotification(email);
        rabbitMQProducer.sendSmsNotification(sms);
    }


    private UserData getUserData() {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDataRepository.findByUserName(userName).orElseThrow(() -> new RuntimeException("User not found"));
    }


    private void setRideBookData(Ride ride, UserData user, DriverDetails driverDetails,Payments payments) {
        payments.setAmount(fareCalculatorImp.calculateFare(ride.getRideType(),10.00));
        payments.setStatus("PENDING");
        ride.setUserData(user);
        ride.setStatus(RideStatus.BOOKED.toString());
        ride.setRideBookTime(LocalDateTime.now());
        ride.setDriverDetails(driverDetails);
        ride.setVehicleDetails(driverDetails.getVehicleDetails());
        ride.setPayments(payments);
    }

    private void setRideCancelData(Ride ride,DriverDetails driverDetails) {
        ride.setStatus(RideStatus.CANCEL.toString());
        ride.setRideCancelTime(LocalDateTime.now());
        ride.setDriverDetails(driverDetails);
    }

    private static void setRideCompleteData(Ride ride, DriverDetails driverDetails) {
        ride.setStatus(RideStatus.COMPLETE.toString());
        ride.setDriverDetails(driverDetails);
        ride.setRideCompleteTime(LocalDateTime.now());
        HashMap<Integer,Integer> map = new HashMap<Integer, Integer>();

    }
}
