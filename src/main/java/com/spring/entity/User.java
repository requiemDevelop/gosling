package com.spring.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.UUID;

import com.spring.dto.model.user.UserDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "users")
public class User {

    @Id
	@GeneratedValue(strategy=GenerationType.UUID)
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "username", unique = true)
    private String username;

    public UserDto toDto() {
        UserDto userDto = new UserDto();
        userDto.setEmail(this.email);
        userDto.setPassword(this.password);
        userDto.setUsername(this.username);
        return userDto;
    }

}
