package com.social.controller;

import com.social.dto.InterestDto;
import com.social.entity.Interest;
import com.social.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/interests")
public class InterestController {

    private final InterestService interestService;

    @GetMapping
    public String index(Model model, @RequestParam("offset") Optional<Integer> offset,
                        @RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("isSort") Optional<Boolean> isSort) {
        Page<Interest> interestPages = interestService.findAll(offset, pageSize, isSort.orElse(false));

        model.addAttribute("interestsPages", interestPages);
        model.addAttribute("interests", interestPages.getContent());
        model.addAttribute("size", pageSize.orElse(5));

        if (interestPages.getTotalPages() > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, interestPages.getTotalPages()).boxed().collect(Collectors.toList()));
        }
        return "interest/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("interest", interestService.findById(id));
        return "interest/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("interest", InterestDto.builder().message("").build());
        return "interest/new";
    }

    @PostMapping()
    public String create(@Valid @ModelAttribute("interest") InterestDto interestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "interest/new";
        }

        if (hasError(interestDto, interestService.save(interestDto).getMessage())) {
            return "interest/new";
        }
        return "redirect:/interests";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long id) {
        model.addAttribute("interest", interestService.findById(id));
        return "interest/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute("interest") InterestDto interestDto,
                         BindingResult bindingResult, @PathVariable("id") Long id) {
        if (bindingResult.hasErrors()) {
            interestDto.setMessage("");
            return "interest/edit";
        }
        InterestDto update = interestService.update(id, interestDto);

        if (hasError(interestDto, update.getMessage())) {
            return "interest/edit";
        }
        return "redirect:/interests";
    }

    private boolean hasError(InterestDto interestDto, String message) {
        if (!message.isEmpty()) {
            interestDto.setMessage(message);
            return true;
        }
        return false;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        interestService.delete(id);
        return "redirect:/interests";
    }
}
