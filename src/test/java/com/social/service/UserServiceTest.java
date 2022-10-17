package com.social.service;

import com.social.entity.Profile;
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

import java.util.Optional;

import static com.social.Constants.*;
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
    public void registerShouldThrowExceptionWhenUsernameIsEmpty() {
        User user = User.builder().username("").password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameLessThan4() {
        User user = User.builder().username("use").password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameMoreThan50() {
        User user = User.builder().username("abcdefgsdfghjkjjhvbnhjknkmabcdefgabcdefgabcdefgabcde")
                .password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenPasswordIsEmpty() {
        User user = User.builder().username(USERNAME).password("").status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = Profile.builder().firstname("Anduhoogfoooodfiuytyryuioiuytyuiouiytyuioiuytryuiopiuytrtyuighjuyy")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = Profile.builder().firstname("A")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = Profile.builder().firstname("")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = Profile.builder().firstname("andrey")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("Lovchinovchinovchinovchinovchinovchinovchinovchinovchinovchinovchin").email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("L").email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("").email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("lovchin").email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenAgeLessThan6() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(5).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenAgeMoreThan120() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(121).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenMailNotValid() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email("gimail.ru").age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldRegisterUser() {
        User user = User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build();
        Profile profile = Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).build();

        given(profileService.save(isA(Profile.class), isA(User.class))).willReturn(profile);
        given(userRepository.save(isA(User.class))).willReturn(user);

        userService.register(user, profile);

        verify(userRepository, Mockito.times(1)).save(user);
        verify(profileService, Mockito.times(1)).save(profile, user);
    }


    @Test
    public void findByUsernameShouldThrowExceptionWhenUserNotFound() {
        given(userRepository.findByUsername(isA(String.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(USERNAME));
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        User expected = new User(USERNAME, PASSWORD);

        given(userRepository.findByUsername(isA(String.class))).willReturn(Optional.of(expected));
        User actual = userService.findByUsername(USERNAME);

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenUserNotFound() {
        given(userRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnUser() {
        User expected = new User(USERNAME, PASSWORD);

        given(userRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        User actual = userService.findById(ID);

        assertEquals(expected, actual);
    }
}
