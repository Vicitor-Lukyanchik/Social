package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.social.Constants.*;
import static com.social.util.MockUtils.createProfile;
import static com.social.util.MockUtils.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void cleanUp(){
        profileRepository.deleteAll();
        profileRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void test() {
        assertThat(profileRepository).isNotNull();
    }

    @Test
    public void saveShouldThrowExceptionWhenFirstnameLengthMoreThan50() {
        User user = userRepository.save(createUser());

        Profile profile = createProfile(user);
        profile.setFirstname(MORE_THAN_50);

        assertThrows(Exception.class, () -> profileRepository.save(profile));
    }

    @Test
    public void saveShouldSaveProfile() {
        User user = userRepository.save(createUser());

        Profile expected = createProfile(user);

        Profile actual = profileRepository.save(expected);

        assertEquals(expected.getFirstname(), actual.getFirstname());
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getSex(), actual.getSex());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getUser(), actual.getUser());
    }
}
