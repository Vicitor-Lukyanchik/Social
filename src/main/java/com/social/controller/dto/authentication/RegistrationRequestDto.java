package com.social.controller.dto.authentication;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * @author Victor Lukyanchik
 * @version 1.0
 * @since 01.06.2022
 */
@Data
public class RegistrationRequestDto {

    @Size(min = 6, max = 30, message = "Username should be more then 6 and less than 30")
    private String username;

    @Size(min = 8, max = 15, message = "Password should be more then 8 and less than 15")
    private String password;

    @Size(min = 8, max = 15, message = "Password should be more then 8 and less than 15")
    private String repeatedPassword;

    @Size(min = 2, max = 50, message = "Firstname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in firstname should be uppercase")
    private String firstname;

    @Size(min = 2, max = 50, message = "Lastname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in lastname should be uppercase")
    private String lastname;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 6, message = "Age should be more than 6")
    @Max(value = 120, message = "Age should less than 120")
    private Integer age;
}
