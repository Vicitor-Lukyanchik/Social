package com.social.controller;

import com.social.controller.dto.authentication.AuthenticationRequestDto;
import com.social.controller.dto.authentication.RegistrationRequestDto;
import com.social.controller.exception.PasswordRepeatException;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.security.jwt.JwtTokenProvider;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @GetMapping("/login")
    public String loginTemplate(@ModelAttribute("login") AuthenticationRequestDto requestDto) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);
            jwtTokenProvider.createToken(username, user.getRoles());
            return "redirect:/profiles/" + user.getId() + "/edit";
        } catch (AuthenticationException e) {
            redirectAttributes.addFlashAttribute("isNotLogin", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerTemplate(@ModelAttribute("register") RegistrationRequestDto requestDto) {
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

            User user = User.builder()
                    .username(requestDto.getUsername())
                    .password(requestDto.getPassword())
                    .build();

            Profile profile = Profile.builder()
                    .firstname(requestDto.getFirstname())
                    .lastname(requestDto.getLastname())
                    .email(requestDto.getEmail()).age(requestDto.getAge())
                    .build();

            User registeredUser = userService.register(user, profile);
            jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getRoles());
            return "redirect:/profiles/" + registeredUser.getId() + "/edit";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isNotRegister", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/register";
        }
    }

}
