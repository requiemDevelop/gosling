package com.spring.service;

import com.spring.dto.api.UpdateUserPasswordDto;
import com.spring.dto.api.UpdateUserProfileDto;
import com.spring.dto.model.user.UserDto;
import com.spring.entity.User;

public interface UserService {

    User getUserById(String userId);

    User getUserByEmail(String email);

    User getUserByUsername(String username);

    User updateProfile(UpdateUserProfileDto updateUserProfileDto);

    User changePassword(UpdateUserPasswordDto updateUserPasswordDto);

    User createUser(UserDto userDto);

}
