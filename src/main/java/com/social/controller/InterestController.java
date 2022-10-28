package com.social.controller;

import com.social.controller.converter.InterestConverter;
import com.social.controller.dto.InterestDto;
import com.social.entity.Interest;
import com.social.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/interests")
public class InterestController {

    private final InterestConverter interestConverter;
    private final InterestService interestService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("interests", interestService.findAll());
        return "interest/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("interest", interestService.findById(id));
        return "interest/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("interest") Interest interest) {
        return "interest/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("interest") InterestDto interestDto) {
        interestService.save(interestConverter.convertToInterest(interestDto));
        return "redirect:/interest";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("interest", interestConverter.convert(interestService.findById(id)));
        return "interest/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") Long id, @ModelAttribute InterestDto interestDto) {
        interestService.update(id, interestConverter.convertToInterest(interestDto));
        return "redirect:/interest";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        interestService.delete(id);
        return "redirect:/interest";
    }
}
