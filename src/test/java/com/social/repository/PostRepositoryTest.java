package com.social.repository;

import com.social.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void test() {
        assertThat(postRepository).isNotNull();
    }

    @Test
    public void save_ShouldSavePost() {
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));
        Profile profile = profileRepository.save(new Profile("Igor", "Pirov", "igorpirov@mail.ru",
                Sex.MALE, 25, user));
        Interest interest = interestRepository.save(new Interest("football"));
        Group group = groupRepository.save(new Group("my group", interest, profile));

        Chat chat = chatRepository.save(new Chat("Football is good", new ArrayList<>()));

        Post expected = new Post(group, chat, "Football is good",
                "wertyuiopottyuio tertyyuu jjhggffd gghjjk mn", LocalDateTime.now());
        Post actual = postRepository.save(expected);
        assertEquals(expected, actual);
    }
}
