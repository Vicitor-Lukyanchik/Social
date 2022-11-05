package com.social.controller;

import com.social.converter.ProfileConverter;
import com.social.converter.RegistrationRequestToProfileConverter;
import com.social.converter.RegistrationRequestToUserConverter;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.exception.PasswordRepeatException;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.security.jwt.JwtTokenProvider;
import com.social.service.UserService;
import com.social.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
@Validated
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RegistrationRequestToUserConverter requestToUserConverter;
    private final RegistrationRequestToProfileConverter requestToProfileConverter;

    @GetMapping("/login")
    public String loginTemplate(@Valid @ModelAttribute("login") AuthenticationRequestDto requestDto) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) throws UserNotFoundException {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            jwtTokenProvider.createToken(username, user.getRoles());
            return "redirect:/profiles/" + user.getId();
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("isNotLogin", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerTemplate(@Valid @ModelAttribute("register") RegistrationRequestDto requestDto) {
        return "authentication/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("register") RegistrationRequestDto requestDto, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "authentication/register";
        }

        try {
            if (!requestDto.getPassword().equals(requestDto.getRepeatedPassword())) {
                throw new PasswordRepeatException("Password should repeat correct");
            }

            User registeredUser = userService.register(requestToUserConverter.convert(requestDto),
                    requestToProfileConverter.convert(requestDto));
            jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getRoles());
            return "redirect:/profiles/" + registeredUser.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isNotRegister", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/register";
        }
    }
}
