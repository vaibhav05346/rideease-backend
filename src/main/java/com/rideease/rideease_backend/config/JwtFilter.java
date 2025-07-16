package com.rideease.rideease_backend.config;

import com.rideease.rideease_backend.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtFilter(JwtUtils jwtUtils)
    {
        this.jwtUtils=jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {


        System.out.println("Request came");
        String authHeader = request.getHeader("Authorization");
        System.out.println(authHeader);

        if(authHeader != null && authHeader.startsWith("Bearer"))
        {
            System.out.println("Token is present");
            boolean validate = jwtUtils.validate(authHeader.substring(7));
            if(validate) {
                String username = jwtUtils.getUserName(authHeader.substring(7));
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                filterChain.doFilter(request, response);
            }
                else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Invalid or expired token!");
                }
                return;
        }

        else{
                if(request.getRequestURI().contains("auth"))
                {
                    filterChain.doFilter(request,response);
                    return;
                    }

                else{
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Missing token!");
                }
            }
    }
}
