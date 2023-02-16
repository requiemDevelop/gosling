package com.spring.service;

public interface PasswordValidator {

    void passwordMatches(String rawPassword, String hashPassword);
    
}
