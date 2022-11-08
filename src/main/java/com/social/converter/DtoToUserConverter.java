package com.social.converter;

import com.social.dto.UserDto;
import com.social.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToUserConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto userDto){
        return User.builder()
                .username(userDto.getUsername())
                .id(userDto.getId()).roles(userDto.getRoles())
                .password(userDto.getPassword())
                .status(userDto.getStatus()).build();
    }
}
