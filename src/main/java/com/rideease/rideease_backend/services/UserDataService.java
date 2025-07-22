package com.rideease.rideease_backend.services;

import com.rideease.rideease_backend.entity.UserData;
import com.rideease.rideease_backend.repository.UserDataRepository;
import com.rideease.rideease_backend.utils.JwtUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataService {

    private final UserDataRepository userDataRepository;
    private final JwtUtils jwtUtils;

    public UserDataService(UserDataRepository userDataRepository,JwtUtils jwtUtils)
    {
        this.userDataRepository=userDataRepository;
        this.jwtUtils=jwtUtils;
    }

    public UserData signUpUser(UserData userData)
    {
        Optional<UserData> isExisting = userDataRepository.findByUserName(userData.getUserName());
        if(isExisting.isPresent()){
            throw new RuntimeException("User Data ALready exist");
        }
        return userDataRepository.save(userData);
    }

    public String loginUser(UserData userData)
    {
        Optional<UserData> isExisting = userDataRepository.findByUserName(userData.getUserName());
        if(isExisting.isEmpty())
            throw new RuntimeException("Invalid UserName");

        if(userData.getPassword().equals(isExisting.get().getPassword()))
            return jwtUtils.generateToken(isExisting.get().getUserName());
        else
            throw new RuntimeException("Invalid Password");
    }


}
