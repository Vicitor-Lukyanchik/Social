package com.social.repository;

import com.social.entity.Status;
import com.social.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.social.Constants.PASSWORD;
import static com.social.Constants.USERNAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
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
        User user = new User("zxcvbnmdfghjkqwertyuiopasdfghjklqwertyuiopasdfghjkl", PASSWORD);

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    public void saveShouldSaveUser() {
        User expected = new User(USERNAME, PASSWORD, Status.ACTIVE);
        User actual = userRepository.save(expected);
        assertEquals(expected.getUsername(), actual.getUsername());
    }
}
