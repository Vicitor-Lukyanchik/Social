package com.social.controller.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Size;

@Builder
@Data
public class InterestDto {

    private Long id;

    @Size(min = 1, max = 50, message = "Interest name should be less than 50 and more than 1")
    private String name;
}
