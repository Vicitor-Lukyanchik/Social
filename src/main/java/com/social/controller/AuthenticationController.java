package com.social.controller;

import com.social.converter.RegistrationRequestToProfileConverter;
import com.social.converter.RegistrationRequestToUserConverter;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.exception.PasswordRepeatException;
import com.social.entity.User;
import com.social.security.jwt.JwtTokenProvider;
import com.social.service.UserService;
import com.social.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginTemplate(@ModelAttribute("login") AuthenticationRequestDto requestDto) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) {
        return userService.login(requestDto, redirectAttributes);
    }

    @GetMapping("/register")
    public String registerTemplate(@ModelAttribute("register") RegistrationRequestDto requestDto) {
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("register") RegistrationRequestDto requestDto, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "authentication/register";
        }
        return userService.register(requestDto, bindingResult, redirectAttributes);
    }
}
