package com.social.service;

import com.social.repository.UserRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private ProfileService profileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;
}
