package com.spring.dto.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {

    @Email
	@NotEmpty
    private String email;

	@NotEmpty
	@Size(min = 6, max = 40, message = "Password length must be between 6 and 40 characters")
    private String password;

	@NotEmpty
	@Size(min = 4, max = 25, message = "Username length must be between 4 and 25 characters")
    private String username;
    
}