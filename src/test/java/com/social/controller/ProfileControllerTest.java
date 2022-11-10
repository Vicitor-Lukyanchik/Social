package com.social.controller;

import com.social.dto.ProfileDto;
import com.social.service.ProfileService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.social.Constants.ID;
import static com.social.util.MockUtils.createProfileDto;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private ProfileController profileController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileService profileService;

    @Test
    public void getInterestById() throws Exception {
        given(profileService.findById(isA(Long.class))).willReturn(createProfileDto());
        mockMvc.perform(get("/profiles/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("profile"))
                .andExpect(view().name("profile/index"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getEditProfile() throws Exception {
        given(profileService.findById(isA(Long.class))).willReturn(createProfileDto());
        mockMvc.perform(get("/profiles/{id}/edit", ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("profile"))
                .andExpect(view().name("profile/edit"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void postUpdateInterest() throws Exception {
        given(profileService.update(isA(Long.class), isA(ProfileDto.class))).willReturn(createProfileDto());
        mockMvc.perform(post("/profiles/{id}", ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/profiles/" + ID))
                .andReturn();
    }
}
