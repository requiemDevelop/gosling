package com.spring.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.spring.dto.api.UpdateUserPasswordDto;
import com.spring.dto.api.UpdateUserProfileDto;
import com.spring.dto.model.user.UserDto;
import com.spring.entity.User;
import com.spring.exception.AlreadyUsedValueException;
import com.spring.exception.ResourceNotFoundException;
import com.spring.repository.UserRepository;
import com.spring.util.PostgresErrorMessageToExceptionExtractor;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private PostgresErrorMessageToExceptionExtractor postgresErrorMessageToExceptionExtractor;
    
    @Autowired
    private PasswordValidator passwordValidator;
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(String userId) {
        return userRepository
            .findById(UUID.fromString(userId))
            .orElseThrow(() -> new ResourceNotFoundException("User not found for this id: " + userId));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @Override
    public User createUser(UserDto userDto) throws AlreadyUsedValueException {
        try {
            return tryCreateUser(userDto);
        } catch (DataIntegrityViolationException ex) {
            String details = postgresErrorMessageToExceptionExtractor.getExceptionDetails(ex);
            throw new AlreadyUsedValueException(details);
        }
    }

    public User tryCreateUser(UserDto userDto) {
        User user = new User()
            .setEmail(userDto.getEmail())
            .setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()))
            .setUsername(userDto.getUsername());
        return userRepository.save(user);
    }
    
    @Override
    public User updateProfile(UpdateUserProfileDto updateUserProfileDto) {
        User user = userRepository.findById(updateUserProfileDto.getUserId()).orElseThrow();
        if (updateUserProfileDto.getEmail() != null) {
            user.setEmail(updateUserProfileDto.getEmail());
        }
        if (updateUserProfileDto.getUsername() != null) {
            user.setUsername(updateUserProfileDto.getUsername());
        }
        // TODO: добавить проверку обязательных полей во все DTO, где это надо
        return userRepository.save(user);
    }

    @Override
    public User changePassword(UpdateUserPasswordDto updateUserPasswordDto) {
        User user = userRepository.findById(updateUserPasswordDto.getUserId()).orElseThrow();

        passwordValidator.passwordMatches(updateUserPasswordDto.getNewPassword(), user.getPassword());

        user.setPassword(updateUserPasswordDto.getNewPassword());

        return userRepository.save(user);
    }

}
