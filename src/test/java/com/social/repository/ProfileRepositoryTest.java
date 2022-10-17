package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.social.Constants.*;
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
    public void saveShouldThrowExceptionWhenFirstnameLengthMoreThan50() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());

        Profile profile = Profile.builder()
                .firstname("Igorigorgorigorgorigorgorigorgorigorgorigorgorigorgo")
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).build();

        assertThrows(Exception.class, () -> profileRepository.save(profile));
    }

    @Test
    public void saveShouldSaveProfile() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());

        Profile expected = Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).build();

        Profile actual = profileRepository.save(expected);
        assertEquals(expected.getFirstname(), actual.getFirstname());
    }
}
