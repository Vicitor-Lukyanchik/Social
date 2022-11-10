package com.social.controller;

import com.social.dto.ProfileDto;
import com.social.dto.UserDto;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.service.ProfileService;
import com.social.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.social.Constants.ID;
import static com.social.util.MockUtils.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController profileController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;


    @Test
    public void getLogin() throws Exception {
        given(userService.findById(isA(Long.class))).willReturn(createUserDto());
        mockMvc.perform(get("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("login"))
                .andExpect(view().name("authentication/login"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void getRegister() throws Exception {
        given(userService.findById(isA(Long.class))).willReturn(createUserDto());
        mockMvc.perform(get("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(model().size(1))
                .andExpect(model().attributeExists("register"))
                .andExpect(view().name("authentication/register"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void postLogin() throws Exception {
        UserDto userDto = createUserDto();
        userDto.setId(ID);
        given(userService.login(isA(AuthenticationRequestDto.class))).willReturn(userDto);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/profiles/" + ID))
                .andReturn();
    }

    @Test
    public void postRegister() throws Exception {
        given(userService.register(isA(RegistrationRequestDto.class))).willReturn(createUserDto());
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(model().size(0))
                .andExpect(view().name("redirect:/profiles/"))
                .andReturn();
    }
}
