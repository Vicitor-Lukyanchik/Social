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
    public void save_ShouldThrowException_WhenFirstnameMoreThan50() {
        Profile profile = new Profile("Anduhoogfoooodfiuytyryuioiuytyuiouiytyuioiuytryuiopiuytrtyuighjuyy",
                "Lovchin", "gig@gmail.com", 12);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenFirstnameLessThan2() {
        Profile profile = new Profile("A", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenFirstnameIsEmpty() {
        Profile profile = new Profile("", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenFirstLetterInFirstnameLowercase() {
        Profile profile = new Profile("andrey", "Lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenLastnameMoreThan50() {
        Profile profile = new Profile("Andrey",
                "Lovchinovchinovchinovchinovchinovchinovchinovchinovchinovchinovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenLastnameLessThan2() {
        Profile profile = new Profile("Andrey", "D", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenLastnameIsEmpty() {
        Profile profile = new Profile("Andrey", "", "gig@gmail.com", 18);

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenFirstLetterInLastnameLowercase() {
        Profile profile = new Profile("Andrey", "lovchin", "gig@gmail.com", 18);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenAgeLessThan6() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 5);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenAgeMoreThan120() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", 121);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenMailNotValid() {
        Profile profile = new Profile("Andrey", "Lovchin", "gigmailcom", 11);

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void save_ShouldSaveProfile() {
        User user = new User("useruser", "23451234");
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18, user);

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);

        profileService.save(profile, user);

        verify(profileRepository, Mockito.times(1)).save(profile);
    }
    
    @Test
    public void update_ShouldThrowException_WhenTownFirstLetterLowercase() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "baranovichi", "336344224", "married");

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }
    
    @Test
    public void update_ShouldThrowException_WhenTownLessThan2() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "B", "336344224", "married");

        assertThrows(ServiceTestException.class, () -> validator.validate(profile));
    }

    @Test
    public void update_ShouldThrowException_WhenTownMoreThan50() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Bfghjkllkjhgfdfghjkllkjhgfdfghjklkjhgcdfghjkllkjhgfdfghjk", "336344224", "married");

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void update_ShouldThrowException_WhenPhoneLessThan7() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "3363", "married");

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void update_ShouldThrowException_WhenPhoneMoreThan15() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "331234567890087663", "married");

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void update_ShouldThrowException_WhenPhoneMoreThan50() {
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "3312363", "xfcdcvgfbhfnjmkkfjhnngvddddfcdsdfghjkjkhbgfdsdfghjk");

        assertThrows(ValidationException.class, () -> validator.validate(profile));
    }

    @Test
    public void update_ShouldUpdateProfile() {
        User user = new User(1L, "useruser", "23451234", new ArrayList<>(), Status.ACTIVE);
        Profile profile = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "336344224", "married", user);

        given(profileRepository.save(isA(Profile.class))).willReturn(profile);

        profileService.update(profile);

        verify(profileRepository, Mockito.times(1)).save(profile);
    }

    @Test
    public void createChat_ShouldThrowException_WhenProfileNotExist() {
        Profile profile = new Profile(1L, "Andrey", "Lovchin", "gig@gmail.com",  18);
        Profile anotherProfile = new Profile(2L, "Alex", "Lovchin", "gig1@gmail.com",  19);

        given(profileRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.createChat(profile, anotherProfile, "chat"));
    }

    @Test
    public void createChat_ShouldCreateChat() {
        Profile profile = new Profile(1L, "Andrey", "Lovchin", "gig@gmail.com",  18);
        Profile anotherProfile = new Profile(2L, "Alex", "Lovchin", "gig1@gmail.com",  19);

        given(profileRepository.findById(1L)).willReturn(Optional.of(profile));
        given(profileRepository.findById(2L)).willReturn(Optional.of(anotherProfile));
        when(chatService.save(isA(Chat.class))).thenReturn(null);

        profileService.createChat(profile, anotherProfile, "chat");

        verify(chatService, Mockito.times(1))
                .save(new Chat("chat", Arrays.asList(profile, anotherProfile)));
    }

    @Test
    public void joinInGroup_ShouldThrowException_WhenProfileNotExist() {
        Profile profile = new Profile(1L, "Andrey", "Lovchin", "gig@gmail.com",  18);
        Group group = new Group("", new Interest(), new Profile());

        given(profileRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile, group));
    }

    @Test
    public void joinInGroup_ShouldThrowException_WhenGroupNotExist() {
        Profile profile = new Profile(1L, "Andrey", "Lovchin", "gig@gmail.com",  18);
        Group group = new Group("", new Interest(), new Profile());

        given(profileRepository.findById(1L)).willReturn(Optional.of(profile));
        given(groupService.isExist(isA(Group.class))).willReturn(false);

        assertThrows(ServiceException.class, () -> profileService.joinInGroup(profile, group));
    }

    @Test
    public void joinInGroup_ShouldJoinInGroup() {
        Profile profile = new Profile(1L, "Andrey", "Lovchin", "gig@gmail.com",  18);
        profile.setJoinGroups(new ArrayList<>());
        Group group = new Group("", new Interest(), new Profile());

        given(profileRepository.findById(1L)).willReturn(Optional.of(profile));
        given(groupService.isExist(isA(Group.class))).willReturn(true);
        given(profileService.update(isA(Profile.class))).willReturn(null);

        profileService.joinInGroup(profile, group);

        verify(groupService, Mockito.times(1)).isExist(group);
    }

    @Test
    public void findByUser_ShouldThrowException_WhenProfileNotFound() {
        User user = new User("useruser", "23451234");
        given(profileRepository.findByUser(isA(User.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.findByUser(user));
    }

    @Test
    public void findByUser_ShouldReturnProfile() {
        User user = new User("useruser", "23451234");
        Profile expected = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "336344224", "married");

        given(profileRepository.findByUser(isA(User.class))).willReturn(Optional.of(expected));
        Profile actual = profileService.findByUser(user);

        assertEquals(expected, actual);
    }

    @Test
    public void findById_ShouldThrowException_WhenProfileNotFound() {
        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> profileService.findById(1l));
    }

    @Test
    public void findById_ShouldReturnProfile() {
        Profile expected = new Profile("Andrey", "Lovchin", "gig@gmail.com", Sex.MALE, 18,
                "Baranovichi", "336344224", "married");

        given(profileRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        Profile actual = profileService.findById(1l);

        assertEquals(expected, actual);
    }
}
