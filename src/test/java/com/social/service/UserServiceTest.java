package com.social.service;

import com.social.entity.Profile;
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
import static com.social.util.MockUtils.*;
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
        User user = createUser();
        user.setUsername(EMPTY_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameLessThan4() {
        User user = createUser();
        user.setUsername(LESS_THAN_4);

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenUsernameMoreThan50() {
        User user = createUser();
        user.setUsername(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenPasswordIsEmpty() {
        User user = createUser();
        user.setPassword(EMPTY_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(user));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = createProfile();
        profile.setFirstname(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = createProfile();
        profile.setFirstname(LESS_THAN_2);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = createProfile();
        profile.setFirstname(EMPTY_STRING);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = createProfile();
        profile.setLastname(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = createProfile();
        profile.setLastname(LESS_THAN_2);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = createProfile();
        profile.setLastname(EMPTY_STRING);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenAgeLessThan6() {
        Profile profile = createProfile();
        profile.setAge(AGE_LESS_THAN_6);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenAgeMoreThan120() {
        Profile profile = createProfile();
        profile.setAge(AGE_MORE_THAN_120);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldThrowExceptionWhenMailNotValid() {
        Profile profile = createProfile();
        profile.setEmail(NOT_VALID_EMAIL);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void registerShouldRegisterUser() throws ServiceException {
        roleRepository.save(createRole());
        User expected = createUser();
        Profile profile = createProfile();

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
        User expected = userRepository.save(createUser());

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
        User expected = userRepository.save(createUser());

        User actual = userService.findById(ID);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }
}
