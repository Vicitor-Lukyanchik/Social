package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.Status;
import com.social.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        assertThat(profileRepository).isNotNull();
    }

    @Test
    public void save_ShouldThrowException_WhenFirstnameLengthMoreThan50() {
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));

        Profile profile = new Profile("Igorigorgorigorgorigorgorigorgorigorgorigorgorigorgo",
                "Pirov", "igorpirov@mail.ru", "MALE", 25, user);

        assertThrows(Exception.class, () -> profileRepository.save(profile));
    }

    @Test
    public void save_ShouldThrowException_WhenSexNotMaleOrFemale() {
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));

        Profile profile = new Profile("Igorgo", "Pirov", "igorpirov@mail.ru", "MLE", 25, user);

        assertThrows(Exception.class, () -> profileRepository.save(profile));
    }

    @Test
    public void save_ShouldSaveProfile() {
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));

        Profile expected = new Profile("Igor", "Pirov", "igorpirov@mail.ru",
                "MALE", 25, user);

        Profile actual = profileRepository.save(expected);
        assertEquals(expected, actual);
    }
}
