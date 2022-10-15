package com.social.service;

import com.social.entity.Role;
import com.social.entity.Status;
import com.social.repository.RoleRepository;
import com.social.service.exception.ServiceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void findByUsername_ShouldThrowException_WhenUserNotFound() {
        given(roleRepository.findByName(isA(String.class))).willReturn(new ArrayList<>());

        assertThrows(ServiceException.class, () -> roleService.findByName("useruser"));
    }

    @Test
    public void findByUsername_ShouldReturnUser() {
        List<Role> role = Arrays.asList(new Role("user", Status.ACTIVE));

        given(roleRepository.findByName(isA(String.class))).willReturn(role);
        List<Role> actual = roleService.findByName("user");

        assertEquals(role, actual);
    }
}
