package com.social.service;

import com.social.entity.Chat;
import com.social.entity.Group;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.repository.ProfileRepository;
import com.social.repository.UserRepository;
import com.social.service.exception.ServiceException;
import com.social.validator.BeanValidator;
import com.social.validator.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.social.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ChatService chatService;

    @MockBean
    private GroupService groupService;

    @Autowired
    private BeanValidator validator;

    @AfterEach
    void cleanUp() {
        profileRepository.deleteAll();
        profileRepository.flush();
        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = Profile.builder().firstname(MORE_THAN_50)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = Profile.builder().firstname(LESS_THAN_2)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = Profile.builder().firstname(EMPTY_STRING)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = Profile.builder().firstname(LOWERCASE_STRING)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(MORE_THAN_50).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LESS_THAN_2).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(EMPTY_STRING).email(EMAIL).age(AGE).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LOWERCASE_STRING).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeLessThan6() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(5).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeMoreThan120() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(121).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenMailNotValid() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(NOT_VALID_EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldSaveProfile() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());
        Profile expected = Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).build();

        Profile actual = profileService.save(expected, user);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getSex(), actual.getSex());
    }

    @Test
    public void updateShouldThrowExceptionWhenTownFirstLetterLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(LOWERCASE_STRING)
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(LESS_THAN_2)
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(MORE_THAN_50)
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneLessThan7() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(LESS_THAN_7).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan15() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(MORE_THAN_15).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(PHONE).familyStatus(MORE_THAN_50).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldUpdateProfile() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());
        Profile expected = profileRepository.save(Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(PHONE).familyStatus(FAMILY_STATUS).user(user).build());

        Profile actual = profileService.update(expected.getId(), expected);

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getSex(), actual.getSex());
        assertEquals(expected.getTown(), actual.getTown());
        assertEquals(expected.getPhone(), actual.getPhone());
        assertEquals(expected.getFamilyStatus(), actual.getFamilyStatus());
    }

    @Test
    public void createChatShouldThrowExceptionWhenProfileNotExist() {
        assertThrows(ServiceException.class, () -> profileService.createChat(ID, ANOTHER_ID, CHAT_NAME));
    }

    @Test
    public void createChatShouldCreateChat() {
        User user = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        User anotherUser = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).user(user).build());
        Profile anotherProfile = profileRepository.save(Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).user(anotherUser).build());

        when(chatService.save(isA(Chat.class))).thenReturn(null);

        profileService.createChat(profile.getId(), anotherProfile.getId(), CHAT_NAME);
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenProfileNotExist() {
        assertThrows(ServiceException.class, () -> profileService.joinInGroup(ID, ID));
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenGroupNotExist() {
        User user = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).user(user).build());
        Group group = Group.builder().name(GROUP_NAME).profile(profile).build();

        given(groupService.isExist(isA(Long.class))).willReturn(false);

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile.getId(), group.getId()));
    }

    @Test
    public void joinInGroupShouldJoinInGroup() {
        User user = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).user(user).build());
        Group group = Group.builder().id(ID).name(GROUP_NAME).profile(profile).build();

        given(groupService.isExist(isA(Long.class))).willReturn(true);

        profileService.joinInGroup(profile.getId(), group.getId());

        verify(groupService, Mockito.times(1)).isExist(ID);
    }

    @Test
    public void findByUserShouldThrowExceptionWhenProfileNotFound() {
        assertThrows(ServiceException.class, () -> profileService.findByUserId(ID));
    }

    @Test
    public void findByUserIdShouldReturnProfile() {
        User user = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        Profile expected = profileRepository.save(Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).user(user).build());

        Profile actual = profileService.findByUserId(user.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenProfileNotFound() {
        assertThrows(ServiceException.class, () -> profileService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnProfile() {
        User user = userRepository.save(User.builder().username(USERNAME)
                .password(PASSWORD).status(STATUS).build());
        Profile expected = profileRepository.save(Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).sex(SEX).user(user).town(TOWN).build());

        Profile actual = profileService.findById(expected.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getSex(), actual.getSex());
    }
}
