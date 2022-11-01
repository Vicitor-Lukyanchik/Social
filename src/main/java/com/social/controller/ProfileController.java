package com.social.controller;

import com.social.controller.converter.ProfileConverter;
import com.social.controller.dto.ProfileDto;
import com.social.service.ProfileService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/profiles")
@Validated
public class ProfileController {

    private final ProfileService profileService;
    private final ProfileConverter profileConverter;

    @GetMapping("/{id}")
    public String get(Model model, @PathVariable("id") Long id) throws ServiceException {
        model.addAttribute("profile", profileConverter.convert(profileService.findById(id)));
        return "profile/index";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) throws ServiceException {
        model.addAttribute("profile", profileConverter.convert(profileService.findById(id)));
        return "profile/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @Valid @ModelAttribute ProfileDto profileDto, BindingResult bindingResult) throws ServiceException {
        if (bindingResult.hasErrors()) {
            return "authentication/register";
        }

        profileService.update(id, profileConverter.convertToProfile(profileDto));
        return "redirect:/profiles/" + id;
    }
}