package com.social.service;

import com.social.entity.Profile;
import com.social.entity.Status;
import com.social.entity.User;
import com.social.repository.UserRepository;
import com.social.service.exception.UserNotFoundException;
import com.social.validator.BeanValidator;
import com.social.validator.ServiceTestException;
import com.social.validator.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @Autowired
    private BeanValidator validator;

    @Test
    public void register_ShouldThrowException_WhenUsernameIsEmpty() {
        User user = new User("", "1234", new ArrayList<>(), Status.ACTIVE);

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void register_ShouldThrowException_WhenUsernameLessThan4() {
        User user = new User("use", "23451234");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void register_ShouldThrowException_WhenUsernameMoreThan50() {
        User user = new User("abcdefgsdfghjkjjhvbnhjknkmabcdefgabcdefgabcdefgabcde", "23451234");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void register_ShouldThrowException_WhenPasswordIsEmpty() {
        User user = new User("user", "");

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void register_ShouldThrowException_WhenFirstnameMoreThan50() {
        Profile profile = new Profile("Anduhoogfoooodfiuytyryuioiuytyuiouiytyuioiuytryuiopiuytrtyuighjuyy",
                "Lovchin", "gig@gmail.com", 12);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenFirstnameLessThan2() {
        Profile profile = new Profile("A", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenFirstnameIsEmpty() {
        Profile profile = new Profile("", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenFirstLetterInFirstnameLowercase() {
        Profile profile = new Profile("andrey", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenLastnameMoreThan50() {
        Profile profile = new Profile("Andrey",
                "Lovchinovchinovchinovchinovchinovchinovchinovchinovchinovchinovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenLastnameLessThan2() {
        Profile profile = new Profile("Andrey", "D", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenLastnameIsEmpty() {
        Profile profile = new Profile("Andrey", "", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenFirstLetterInLastnameLowercase() {
        Profile profile = new Profile("Andrey", "lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenAgeLessThan6() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 5);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenAgeMoreThan120() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 121);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldThrowException_WhenMailNotValid() {
        Profile profile = new Profile("Andrey", "Lovchin", "gigmailcom", 11);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void register_ShouldRegisterUser() {
        User user = new User("useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 18);

        given(profileService.save(isA(Profile.class), isA(User.class))).willReturn(profile);
        given(userRepository.save(isA(User.class))).willReturn(user);

        userService.register(user, profile);

        verify(userRepository, Mockito.times(1)).save(user);
        verify(profileService, Mockito.times(1)).save(profile, user);
    }


    @Test
    public void findByUsername_ShouldThrowException_WhenUserNotFound() {
        given(userRepository.findByUsername(isA(String.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername("useruser"));
    }

    @Test
    public void findByUsername_ShouldReturnUser() {
        User expected = new User("useruser", "23451234");

        given(userRepository.findByUsername(isA(String.class))).willReturn(Optional.of(expected));
        User actual = userService.findByUsername("useruser");

        assertEquals(expected, actual);
    }

    @Test
    public void findById_ShouldThrowException_WhenUserNotFound() {
        given(userRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(1l));
    }

    @Test
    public void findById_ShouldReturnUser() {
        User expected = new User("useruser", "23451234");

        given(userRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        User actual = userService.findById(1l);

        assertEquals(expected, actual);
    }
}
