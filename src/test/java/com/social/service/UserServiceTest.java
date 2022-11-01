package com.social.service;

import com.social.entity.Profile;
import com.social.entity.Role;
import com.social.entity.User;
import com.social.repository.RoleRepository;
import com.social.repository.UserRepository;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;
import com.social.validator.BeanValidator;
import com.social.validator.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.social.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private ProfileService profileService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BeanValidator validator;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameIsEmpty() {
        User user = User.builder().username(EMPTY_STRING).password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameLessThan4() {
        User user = User.builder().username(LESS_THAN_4).password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameMoreThan50() {
        User user = User.builder().username(MORE_THAN_50)
                .password(PASSWORD).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenPasswordIsEmpty() {
        User user = User.builder().username(USERNAME).password(EMPTY_STRING).status(STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = Profile.builder().firstname(MORE_THAN_50)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = Profile.builder().firstname(LESS_THAN_2)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = Profile.builder().firstname(EMPTY_STRING)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = Profile.builder().firstname(LOWERCASE_STRING)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(MORE_THAN_50).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LESS_THAN_2).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(EMPTY_STRING).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LOWERCASE_STRING).email(EMAIL).age(AGE).build();

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
                .lastname(LASTNAME).email(NOT_VALID_EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldRegisterUser() throws ServiceException {
        roleRepository.save(Role.builder().name(ROLE_NAME).status(STATUS).build());
        User expected = User.builder().username(USERNAME).password(PASSWORD)
                .status(STATUS).build();
        Profile profile = Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).build();

        given(profileService.save(isA(Profile.class), isA(User.class))).willReturn(profile);
        User actual = userService.register(expected, profile);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }


    @Test
    public void findByUsernameShouldThrowExceptionWhenUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findByUsername(USERNAME));
    }

    @Test
    public void findByUsernameShouldReturnUser() throws UserNotFoundException {
        User expected = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());

        User actual = userService.findByUsername(USERNAME);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnUser() throws UserNotFoundException {
        User expected = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());

        User actual = userService.findById(ID);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }
}
