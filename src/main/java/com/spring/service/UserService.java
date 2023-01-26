package com.spring.service;

import com.spring.dto.model.user.UserDto;

public interface UserService {
    /**
     * Register a new user
     *
     * @param userDto
     * @return
     */
    UserDto signUp(UserDto userDto);

    /**
     * Log in the user
     *
     * @param userDto
     * @return
     */
    UserDto signIn(UserDto userDto);

    /**
     * Update profile of the user
     *
     * @param userDto
     * @return
     */
    UserDto updateProfile(UserDto userDto);

    /**
     * Update password
     *
     * @param newPassword
     * @return
     */
    UserDto changePassword(UserDto userDto, String newPassword);
}
