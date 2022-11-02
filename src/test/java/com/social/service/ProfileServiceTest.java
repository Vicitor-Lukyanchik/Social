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
import static com.social.util.MockUtils.*;
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
    public void cleanUp() {
        profileRepository.deleteAll();
        profileRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = createProfile();
        profile.setFirstname(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = createProfile();
        profile.setFirstname(LESS_THAN_2);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = createProfile();
        profile.setFirstname(EMPTY_STRING);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = createProfile();
        profile.setFirstname(LOWERCASE_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = createProfile();
        profile.setLastname(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = createProfile();
        profile.setLastname(LESS_THAN_2);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = createProfile();
        profile.setLastname(EMPTY_STRING);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeLessThan6() {
        Profile profile = createProfile();
        profile.setAge(AGE_LESS_THAN_6);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeMoreThan120() {
        Profile profile = createProfile();
        profile.setAge(AGE_MORE_THAN_120);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenMailNotValid() {
        Profile profile = createProfile();
        profile.setEmail(NOT_VALID_EMAIL);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldSaveProfile() {
        User user = userRepository.save(createUser());
        Profile expected = createProfile();

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
        Profile profile = createProfile();
        profile.setTown(LOWERCASE_STRING);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownLessThan2() {
        Profile profile = createProfile();
        profile.setTown(LESS_THAN_2);

        assertThrows(ServiceException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownMoreThan50() {
        Profile profile = createProfile();
        profile.setTown(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneLessThan7() {
        Profile profile = createProfile();
        profile.setPhone(LESS_THAN_7);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan15() {
        Profile profile = createProfile();
        profile.setPhone(MORE_THAN_15);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan50() {
        Profile profile = createProfile();
        profile.setPhone(MORE_THAN_50);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldUpdateProfile() throws ServiceException {
        User user = userRepository.save(createUser());
        Profile expected = profileRepository.save(createProfile(user));

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
    public void createChatShouldCreateChat() throws ServiceException {
        User user = userRepository.save(createUser());
        User anotherUser = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Profile anotherProfile = profileRepository.save(createProfile(anotherUser));

        when(chatService.save(isA(Chat.class))).thenReturn(null);

        profileService.createChat(profile.getId(), anotherProfile.getId(), CHAT_NAME);
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenProfileNotExist() {
        assertThrows(ServiceException.class, () -> profileService.joinInGroup(ID, ID));
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenGroupNotExist() {
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Group group = createGroup(profile);

        given(groupService.isExist(isA(Long.class))).willReturn(false);

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile.getId(), group.getId()));
    }

    @Test
    public void joinInGroupShouldJoinInGroup() throws ServiceException {
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Group group = createGroup(profile);
        group.setId(ID);

        given(groupService.isExist(isA(Long.class))).willReturn(true);

        profileService.joinInGroup(profile.getId(), group.getId());

        verify(groupService, Mockito.times(1)).isExist(ID);
    }

    @Test
    public void findByUserShouldThrowExceptionWhenProfileNotFound() {
        assertThrows(ServiceException.class, () -> profileService.findByUserId(ID));
    }

    @Test
    public void findByUserIdShouldReturnProfile() throws ServiceException {
        User user = userRepository.save(createUser());
        Profile expected = profileRepository.save(createProfile(user));

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
    public void findByIdShouldReturnProfile() throws ServiceException {
        User user = userRepository.save(createUser());
        Profile expected = profileRepository.save(createProfile(user));

        Profile actual = profileService.findById(expected.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getSex(), actual.getSex());
    }
}
