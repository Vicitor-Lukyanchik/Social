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

import static com.social.Constants.ROLE_NAME;
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
    public void findByUsernameShouldThrowExceptionWhenUserNotFound() {
        given(roleRepository.findByName(isA(String.class))).willReturn(new ArrayList<>());

        assertThrows(ServiceException.class, () -> roleService.findByName(ROLE_NAME));
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        List<Role> role = Arrays.asList(new Role(ROLE_NAME, Status.ACTIVE));

        given(roleRepository.findByName(isA(String.class))).willReturn(role);
        List<Role> actual = roleService.findByName(ROLE_NAME);

        assertEquals(role, actual);
    }
}
