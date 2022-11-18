package com.social.controller;

import com.social.dto.InterestDto;
import com.social.entity.Interest;
import com.social.service.InterestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.social.Constants.ID;
import static com.social.util.MockUtils.createInterest;
import static com.social.util.MockUtils.createInterestDto;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InterestControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InterestService interestService;

    @Test
    public void getInterestById() throws Exception {
        given(interestService.findById(isA(Long.class))).willReturn(createInterestDto());
        mockMvc.perform(get("/interests/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("interest"))
                .andExpect(view().name("interest/show"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getInterests() throws Exception {
        List<Interest> interests = Arrays.asList(createInterest());
        given(interestService.findAll(isA(Optional.class), isA(Optional.class), isA(boolean.class)))
                .willReturn(new PageImpl<>(interests, PageRequest.of(0, 5, Sort.by("name")), interests.size()));

        mockMvc.perform(get("/interests")
                        .param("isSort", "on")
                        .param("offset", "0")
                        .param("pageSize", "5"))
                .andExpect(model().size(4))
                .andExpect(model().attributeExists("interests"))
                .andExpect(model().attributeExists("interestsPages"))
                .andExpect(model().attributeExists("size"))
                .andExpect(model().attributeExists("pageNumbers"))
                .andExpect(view().name("interest/index"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getCreateInterest() throws Exception {
        mockMvc.perform(get("/interests/new")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("interest"))
                .andExpect(view().name("interest/new"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void postCreateInterest() throws Exception {
        given(interestService.save(isA(InterestDto.class))).willReturn(createInterestDto());
        mockMvc.perform(post("/interests/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/interests"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }


    @Test
    public void getEditInterest() throws Exception {
        given(interestService.findById(isA(Long.class))).willReturn(createInterestDto());
        mockMvc.perform(get("/interests/{id}/edit", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("interest"))
                .andExpect(view().name("interest/edit"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void postUpdateInterest() throws Exception {
        given(interestService.update(isA(Long.class), isA(InterestDto.class))).willReturn(createInterestDto());
        mockMvc.perform(post("/interests/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/interests"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    public void postDeleteInterest() throws Exception {
        given(interestService.delete(isA(Long.class))).willReturn(createInterestDto());
        mockMvc.perform(post("/interests/{id}/delete", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/interests"))
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }
}
