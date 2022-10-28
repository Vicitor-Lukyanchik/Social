package com.social.controller;

import com.social.controller.converter.ProfileConverter;
import com.social.controller.dto.ProfileDto;
import com.social.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/profiles")
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileConverter profileConverter;

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("profile", profileConverter.convert(profileService.findById(id)));
        return "profile/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute ProfileDto profileDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "authentication/register";
        }

        profileService.update(id, profileConverter.convertToProfile(profileDto));
        return "redirect:/interest";
    }
}