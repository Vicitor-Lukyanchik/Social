package com.social.service;

import com.social.converter.ProfileToDtoConverter;
import com.social.dto.ProfileDto;
import com.social.entity.Chat;
import com.social.entity.Group;
import com.social.entity.Profile;
import com.social.entity.User;
import com.social.repository.ProfileRepository;
import com.social.repository.UserRepository;
import com.social.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ProfileToDtoConverter profileToDtoConverter;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private ChatService chatService;

    @MockBean
    private GroupService groupService;


    @AfterEach
    public void cleanUp() {
        profileRepository.deleteAll();
        profileRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameMoreThan50() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameLessThan2() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(LESS_THAN_2);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setFirstname(LOWERCASE_STRING);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameMoreThan50() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameLessThan2() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(LESS_THAN_2);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameIsEmpty() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setLastname(LOWERCASE_STRING);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeLessThan6() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setAge(AGE_LESS_THAN_6);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenAgeMoreThan120() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setAge(AGE_MORE_THAN_120);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
    }

    @Test
    public void saveShouldThrowExceptionWhenMailNotValid() {
        User user = createUser();
        Profile profile = createProfile();
        profile.setEmail(NOT_VALID_EMAIL);

        assertThrows(ConstraintViolationException.class, () -> profileService.save(profile, user));
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
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setTown(LOWERCASE_STRING);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownLessThan2() {
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setTown(LESS_THAN_2);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownMoreThan50() {
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setTown(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneLessThan7() {
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setPhone(LESS_THAN_7);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan15() {
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setPhone(MORE_THAN_15);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan50() {
        ProfileDto profile = profileToDtoConverter.convert(createProfile());
        profile.setPhone(MORE_THAN_50);

        assertThrows(ConstraintViolationException.class, () -> profileService.update(ID, profile));
    }

    @Test
    public void updateShouldUpdateProfile() {
        User user = userRepository.save(createUser());
        ProfileDto expected = profileToDtoConverter.convert(profileRepository.save(createProfile(user)));

        ProfileDto actual = profileService.update(expected.getId(), expected);

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

        given(groupService.isPresent(isA(Long.class))).willReturn(false);

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile.getId(), group.getId()));
    }

    @Test
    public void joinInGroupShouldJoinInGroup() throws ServiceException {
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Group group = createGroup(profile);
        group.setId(ID);

        given(groupService.isPresent(isA(Long.class))).willReturn(true);

        profileService.joinInGroup(profile.getId(), group.getId());

        verify(groupService, Mockito.times(1)).isPresent(ID);
    }

    @Test
    public void findByUserShouldThrowExceptionWhenProfileNotFound() {
        ProfileDto expected = ProfileDto.builder().message("Profile haven't been founded by user id: " + ID).build();

        ProfileDto actual = profileService.findByUserId(ID);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void findByUserIdShouldReturnProfile() {
        User user = userRepository.save(createUser());
        ProfileDto expected = profileToDtoConverter.convert(profileRepository.save(createProfile(user)));

        ProfileDto actual = profileService.findByUserId(user.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenProfileNotFound() {
        ProfileDto expected = ProfileDto.builder().message("Profile haven't been founded by id : " + ID).build();

        ProfileDto actual = profileService.findById(ID);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void findByIdShouldReturnProfile() {
        User user = userRepository.save(createUser());
        ProfileDto expected = profileToDtoConverter.convert(profileRepository.save(createProfile(user)));

        ProfileDto actual = profileService.findById(expected.getId());

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getSex(), actual.getSex());
    }
}
