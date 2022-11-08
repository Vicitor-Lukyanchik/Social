package com.social.service;

import com.social.entity.Role;
import com.social.repository.RoleRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.social.Constants.ROLE_NAME;
import static com.social.util.MockUtils.createRole;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @AfterEach
    public void cleanUp(){
        roleRepository.deleteAll();
        roleRepository.flush();
    }

    @Test
    public void findByUsernameShouldThrowExceptionWhenUserNotFound() {
        List<Role> expected = Collections.emptyList();

        List<Role> actual = roleService.findByName(ROLE_NAME);

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        Role role = roleRepository.save(createRole());
        List<Role> expected = Arrays.asList(role);

        List<Role> actual = roleService.findByName(ROLE_NAME);

        assertEquals(expected.size(), actual.size());
    }
}
