package com.social.controller;

import com.social.dto.UserDto;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;

    @GetMapping("/login")
    public String loginTemplate(@ModelAttribute("login") AuthenticationRequestDto requestDto) {
        return "authentication/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) {
        UserDto userDto = userService.login(requestDto);
        if (userDto.getMessage().isEmpty()) {
            return "redirect:/profiles/" + userDto.getId();
        } else {
            redirectAttributes.addFlashAttribute("isNotLogin", true);
            redirectAttributes.addFlashAttribute("errorMessage", userDto.getMessage());
            return "redirect:/auth/login";
        }
    }

    @GetMapping("/register")
    public String registerTemplate(@ModelAttribute("register") RegistrationRequestDto requestDto) {
        requestDto.setMessage("");
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("register") RegistrationRequestDto requestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authentication/register";
        }
        UserDto userDto = userService.register(requestDto);

        if (hasError(requestDto, userDto.getMessage())){
            return "authentication/register";
        }
        return "redirect:/profiles/";
    }

    private boolean hasError(RegistrationRequestDto registrationRequestDto, String message) {
        if (!message.isEmpty()) {
            registrationRequestDto.setMessage(message);
            return true;
        }
        return false;
    }
}
