package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordValidatorImpl implements PasswordValidator {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void passwordMatches(String rawPassword, String hashPassword) throws BadCredentialsException {
        try {
            bCryptPasswordEncoder.matches(rawPassword, hashPassword);
        } catch (Exception e) {
            throw new BadCredentialsException("Wrong password");
        }
    }
    
}
