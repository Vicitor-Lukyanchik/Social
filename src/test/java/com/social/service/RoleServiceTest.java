package com.social.service;

import com.social.entity.Role;
import com.social.repository.RoleRepository;
import com.social.service.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.social.Constants.ROLE_NAME;
import static com.social.Constants.STATUS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(ServiceException.class, () -> roleService.findByName(ROLE_NAME));
    }

    @Test
    public void findByUsernameShouldReturnUser() throws ServiceException {
        Role role = roleRepository.save(Role.builder().name(ROLE_NAME).status(STATUS).build());
        List<Role> expected = Arrays.asList(role);

        List<Role> actual = roleService.findByName(ROLE_NAME);

        assertEquals(expected.size(), actual.size());
    }
}
