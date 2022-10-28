package com.social.repository;

import com.social.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.social.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        assertThat(userRepository).isNotNull();
    }

    @Test
    public void saveShouldThrowExceptionWhenUsernameLengthMoreThan50() {
        User user = User.builder().username(MORE_THAN_50).password(PASSWORD).status(STATUS).build();

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    public void saveShouldSaveUser() {
        User expected = User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build();
        User actual = userRepository.save(expected);
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getStatus(), actual.getStatus());
    }
}
