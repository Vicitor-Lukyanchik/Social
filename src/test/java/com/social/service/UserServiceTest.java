package com.social.service;

import com.social.converter.DtoToUserConverter;
import com.social.converter.UserToDtoConverter;
import com.social.dto.UserDto;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.exception.ServiceException;
import com.social.repository.RoleRepository;
import com.social.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.validation.ConstraintViolationException;

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
    private UserToDtoConverter userToDtoConverter;

    @AfterEach
    public void cleanUp() {
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void registerUserShouldThrowExceptionWhenUsernameIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        user.setUsername(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenUsernameLessThan4() {
        Profile profile = createProfile();
        User user = createUser();
        user.setUsername(LESS_THAN_4);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenUsernameMoreThan50() {
        Profile profile = createProfile();
        User user = createUser();
        user.setUsername(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenPasswordIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        user.setPassword(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenFirstnameMoreThan50() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenFirstnameLessThan2() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(LESS_THAN_2);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenFirstnameIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenLastnameMoreThan50() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenLastnameLessThan2() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(LESS_THAN_2);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenLastnameIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenAgeLessThan6() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setAge(AGE_LESS_THAN_6);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenAgeMoreThan120() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setAge(AGE_MORE_THAN_120);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldThrowExceptionWhenMailNotValid() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setEmail(NOT_VALID_EMAIL);

        assertThrows(ConstraintViolationException.class, () -> userService.registerUser(user, profile));
    }

    @Test
    public void registerUserShouldRegisterUser() {
        roleRepository.save(createRole());
        User expected = createUser();
        Profile profile = createProfile();

        given(profileService.save(isA(Profile.class), isA(User.class))).willReturn(profile);
        UserDto actual = userService.registerUser(expected, profile);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());

    }


    @Test
    public void findByUsernameShouldThrowExceptionWhenUserNotFound() {
        UserDto expected = UserDto.builder().message("User haven't founded by username : " + USERNAME).build();

        UserDto actual = userService.findByUsername(USERNAME);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void findByUsernameShouldReturnUser() {
        UserDto expected = userToDtoConverter.convert(userRepository.save(createUser()));

        UserDto actual = userService.findByUsername(USERNAME);

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenUserNotFound() {
        UserDto expected = UserDto.builder().message("User haven't founded by id : " + ID).build();

        UserDto actual = userService.findById(ID);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void findByIdShouldReturnUser() {
        UserDto expected = userToDtoConverter.convert(userRepository.save(createUser()));

        UserDto actual = userService.findById(expected.getId());

        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getId(), actual.getId());
    }
}
