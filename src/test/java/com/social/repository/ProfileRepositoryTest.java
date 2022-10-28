package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.social.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
                .firstname(MORE_THAN_50)
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
        assertEquals(expected.getLastname(), actual.getLastname());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getSex(), actual.getSex());
        assertEquals(expected.getAge(), actual.getAge());
        assertEquals(expected.getUser(), actual.getUser());
    }
}
