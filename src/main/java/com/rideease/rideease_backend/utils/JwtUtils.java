package com.rideease.rideease_backend.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final String secret="itismysupersecretrideeaseprojectJWTsecretkey";
    private final long expire = 1000*60*60;

    private Key getSignKey()
    {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    //Token Generation
    public String generateToken(String userName){
        System.out.println("SignKeyGeneration: " + getSignKey());
        return Jwts.builder()
                .setSubject(userName)
                .setIssuer("RideEase")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expire))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //token validation
    public boolean validate(String token)
    {
        try{
            System.out.println(secret.getBytes().length);
            Jwts.parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token);
                System.out.println("SignKeyParser: " + getSignKey());
            return true;
        }catch (JwtException ex){
            return false;
        }
    }

    //get username
    public String getUserName(String token)
    {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }


}
