package com.demcare.demo.service;

import com.demcare.demo.entities.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


@Service
public class SecurityService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityService.class);

    public String findLoggedInDni() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    public UsernamePasswordAuthenticationToken autoLogin(String dni, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(dni);

        UsernamePasswordAuthenticationToken aToken = new UsernamePasswordAuthenticationToken(
                userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(aToken);
        if (aToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(aToken);
            logger.debug(String.format("Auto login %s successfully!", dni));
        }
        return aToken;
    }

    public void logByToken(String tokenCode){
        User user = tokenService.findByCode(tokenCode).getUser();
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), userDetails.getAuthorities());
        if (token.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(token);
        }else{
            authenticationManager.authenticate(token);
        }
    }
}
