package com.social.service;

import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

public interface UserService {

    String login(AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes);

    String register(RegistrationRequestDto requestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes);

    User registerUser(User user, Profile profile) throws ServiceException;

    User findByUsername(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;
}
