package com.social.service;

import com.social.dto.MessageDto;
import com.social.dto.UserDto;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.exception.ServiceException;
import com.social.exception.UserNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

public interface UserService {

    UserDto login(AuthenticationRequestDto requestDto);

    UserDto register(RegistrationRequestDto requestDto);

    UserDto registerUser(@Valid User user, @Valid Profile profile);

    UserDto findByUsername(String username);

    UserDto findById(Long id);
}
