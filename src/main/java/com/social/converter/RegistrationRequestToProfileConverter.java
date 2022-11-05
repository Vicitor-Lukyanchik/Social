package com.social.converter;

import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.Profile;
import com.social.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestToProfileConverter implements Converter<RegistrationRequestDto, Profile> {

    @Override
    public Profile convert(RegistrationRequestDto requestDto) {
        return Profile.builder()
                .firstname(requestDto.getFirstname())
                .lastname(requestDto.getLastname())
                .email(requestDto.getEmail()).age(requestDto.getAge())
                .build();
    }
}
