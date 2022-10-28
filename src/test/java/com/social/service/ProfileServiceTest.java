package com.social.service;

import com.social.entity.*;
import com.social.repository.ProfileRepository;
import com.social.service.exception.ServiceException;
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
import java.util.Arrays;
import java.util.Optional;

import static com.social.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileServiceTest {

    @Autowired
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private ChatService chatService;

    @MockBean
    private GroupService groupService;

    @Autowired
    private BeanValidator validator;

    @Test
    public void saveShouldThrowExceptionWhenFirstnameMoreThan50() {
        Profile profile = Profile.builder().firstname("Anduhoogfoooodfiuytyryuioiuytyuiouiytyuioiuytryuiopiuytrtyuighjuyy")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameLessThan2() {
        Profile profile = Profile.builder().firstname("A")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameIsEmpty() {
        Profile profile = Profile.builder().firstname("")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInFirstnameLowercase() {
        Profile profile = Profile.builder().firstname("andrey")
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("Lovchinovchinovchinovchinovchinovchinovchinovchinovchinovchinovchin").email(EMAIL).age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("L").email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenLastnameIsEmpty() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("").email(EMAIL).age(AGE).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstLetterInLastnameLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname("lovchin").email(EMAIL).age(AGE).build();

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
                .lastname(LASTNAME).email("gimail.ru").age(AGE).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void saveShouldSaveProfile() {
        User user = User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build();
        Profile profile = Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).build();

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);

        profileService.save(profile, user);

        verify(profileRepository, Mockito.times(1)).save(profile);
    }

    @Test
    public void updateShouldThrowExceptionWhenTownFirstLetterLowercase() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town("baranovichi")
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownLessThan2() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town("B")
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenTownMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town("Bfghjkllkjhgfdfghjkllkjhgfdfghjklkjhgcdfghjkllkjhgfdfghjk")
                .phone(PHONE).familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneLessThan7() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone("3212").familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan15() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone("123456789987654334").familyStatus(FAMILY_STATUS).build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldThrowExceptionWhenPhoneMoreThan50() {
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(PHONE).familyStatus("Bfghjkllkjhgfdfghjkllkjhgfdfghjklkjhgcdfghjkllkjhgfdfghjk").build();

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void updateShouldUpdateProfile() {
        User user = User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build();
        Profile profile = Profile.builder().firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).town(TOWN)
                .phone(PHONE).familyStatus(FAMILY_STATUS).user(user).build();

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);

        profileService.update(profile);

        verify(profileRepository, Mockito.times(1)).save(profile);
    }

    @Test
    public void createChatShouldThrowExceptionWhenProfileNotExist() {
        Profile profile = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();
        Profile anotherProfile = Profile.builder().id(ANOTHER_ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        given(profileRepository.findById(ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.createChat(profile, anotherProfile, "chat"));
    }

    @Test
    public void createChatShouldCreateChat() {
        Profile profile = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();
        Profile anotherProfile = Profile.builder().id(ANOTHER_ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();

        given(profileRepository.findById(ID)).willReturn(Optional.of(profile));
        given(profileRepository.findById(ANOTHER_ID)).willReturn(Optional.of(anotherProfile));
        when(chatService.save(isA(Chat.class))).thenReturn(null);

        profileService.createChat(profile, anotherProfile, CHAT_NAME);

        verify(chatService, Mockito.times(1))
                .save(new Chat(CHAT_NAME, Arrays.asList(profile, anotherProfile)));
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenProfileNotExist() {
        Profile profile = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();
        Group group = Group.builder().name(GROUP_NAME).profile(profile).build();

        given(profileRepository.findById(ID)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile, group));
    }

    @Test
    public void joinInGroupShouldThrowExceptionWhenGroupNotExist() {
        Profile profile = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).build();
        Group group = Group.builder().name(GROUP_NAME).profile(profile).build();

        given(profileRepository.findById(1L)).willReturn(Optional.of(profile));
        given(groupService.isExist(isA(Group.class))).willReturn(false);

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile, group));
    }

    @Test
    public void joinInGroupShouldJoinInGroup() {
        Profile profile = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).joinGroups(new ArrayList<>()).build();
        Group group =Group.builder().name(GROUP_NAME).profile(profile).build();

        given(profileRepository.findById(1L)).willReturn(Optional.of(profile));
        given(groupService.isExist(isA(Group.class))).willReturn(true);
        given(profileService.update(isA(Profile.class))).willReturn(null);

        profileService.joinInGroup(profile, group);

        verify(groupService, Mockito.times(1)).isExist(group);
    }

    @Test
    public void findByUserShouldThrowExceptionWhenProfileNotFound() {
        User user = new User(USERNAME, PASSWORD, Status.ACTIVE);
        given(profileRepository.findByUser(isA(User.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.findByUser(user));
    }

    @Test
    public void findByUserShouldReturnProfile() {
        User user = new User(USERNAME, PASSWORD, Status.ACTIVE);
        Profile expected = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).joinGroups(new ArrayList<>()).build();

        given(profileRepository.findByUser(isA(User.class))).willReturn(Optional.of(expected));
        Profile actual = profileService.findByUser(user);

        assertEquals(expected, actual);
    }

    @Test
    public void findByIdShouldThrowExceptionWhenProfileNotFound() {
        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnProfile() {
        Profile expected = Profile.builder().id(ID).firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL).age(AGE).joinGroups(new ArrayList<>()).build();

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        Profile actual = profileService.findById(ID);

        assertEquals(expected, actual);
    }
}
