package com.social.dto;

import com.social.entity.Sex;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@Builder
public class ProfileDto {

    private Long id;

    @Size(min = 2, max = 50, message = "Firstname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in firstname should be uppercase")
    private String firstname;

    @Size(min = 2, max = 50, message = "Lastname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in lastname should be uppercase")
    private String lastname;

    @Size(max = 100, message = "Email should be less than 100")
    @Email(message = "Email should be valid")
    private String email;

    @Enumerated(EnumType.STRING)
    @Pattern(regexp = "UNDEFINED|MALE|FEMALE", message = "Sex should be : UNDEFINED, MALE, FEMALE")
    private Sex sex;

    @Min(value = 6, message = "Age should be more than 6")
    @Max(value = 120, message = "Age should less than 120")
    private Integer age;

    @Size(min = 2, max = 50, message = "Town name should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in town should be uppercase")
    private String town;

    @Size(min = 7, max = 15, message = "Phone should be more than 7 and less than 15")
    private String phone;

    @Size(max = 50, message = "Family status should be less than 50")
    @Pattern(regexp = "Undefined|married|not married|in love|actively looking", message = "Family status should be : Undefined, married, not married, in love, actively looking")
    private String familyStatus;
}
