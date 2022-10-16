package com.social.repository;

import com.social.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void test() {
        assertThat(groupRepository).isNotNull();
    }

    @Test
    public void save_ShouldSaveGroup() {
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));
        Profile profile = profileRepository.save(new Profile("Igor", "Pirov", "igorpirov@mail.ru",
                Sex.MALE, 25, user));
        Interest interest = interestRepository.save(new Interest("football"));

        Group expected = new Group("my group", interest, profile);
        Group actual = groupRepository.save(expected);
        assertEquals(expected, actual);
    }
}
