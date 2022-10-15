package com.social.repository;

import com.social.entity.Status;
import com.social.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    public void save_ShouldThrowException_WhenUsernameLengthMoreThan50() {
        User user = new User("zxcvbnmdfghjkqwertyuiopasdfghjklqwertyuiopasdfghjkl", "23451234");

        assertThrows(Exception.class, () -> userRepository.save(user));
    }

    @Test
    public void save_ShouldSaveUser() {
        User expected = new User("vicitor", "123432", Status.ACTIVE);
        User actual = userRepository.save(expected);
        assertEquals(expected, actual);
    }
}
