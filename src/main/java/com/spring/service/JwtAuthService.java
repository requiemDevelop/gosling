package com.spring.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.client.HttpClientErrorException.Conflict;

import com.spring.dto.api.LogInUserDto;
import com.spring.dto.jwt.JwtTokensDto;
import com.spring.dto.jwt.RefreshJwtDto;
import com.spring.dto.model.user.UserDto;

public interface JwtAuthService {

    JwtTokensDto registerUser(UserDto userDto) throws Conflict;

    JwtTokensDto logInUser(LogInUserDto logInUserDto) throws BadCredentialsException;

    JwtTokensDto refreshJwt(RefreshJwtDto refreshJwtDto);
    
}
