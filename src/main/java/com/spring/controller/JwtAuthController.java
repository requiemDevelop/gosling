package com.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.dto.api.LogInUserDto;
import com.spring.dto.jwt.JwtTokensDto;
import com.spring.dto.jwt.RefreshJwtDto;
import com.spring.dto.model.user.UserDto;
import com.spring.service.JwtAuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class JwtAuthController {
    
    @Autowired
    private JwtAuthService jwtAuthService;

    @PostMapping("/register")
    public JwtTokensDto registerUser(@RequestBody @Valid UserDto userDto) {
        return jwtAuthService.registerUser(userDto);
    }

    @PostMapping("/logIn")
    public JwtTokensDto logInUser(@RequestBody @Valid LogInUserDto logInUserDto) {
        return jwtAuthService.logInUser(logInUserDto);
    }

    @PostMapping("/refresh")
    public JwtTokensDto refreshJwt(@RequestBody @Valid RefreshJwtDto refreshJwtDto) {
        return jwtAuthService.refreshJwt(refreshJwtDto);
    }

}
