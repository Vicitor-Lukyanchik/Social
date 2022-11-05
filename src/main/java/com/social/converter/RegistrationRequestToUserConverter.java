package com.social.converter;

import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestToUserConverter implements Converter<RegistrationRequestDto, User> {

    @Override
    public User convert(RegistrationRequestDto requestDto) {
        return User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();
    }
}
