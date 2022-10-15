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
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test() {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    public void save_ShouldSaveMessage() {
        Chat chat = chatRepository.save(new Chat("Football", new ArrayList<>()));
        User user = userRepository.save(new User("user", "user", Status.ACTIVE));
        Profile profile = profileRepository.save(new Profile("Igor", "Pirov", "igorpirov@mail.ru",
                "MALE", 25, user));

        Message expected = new Message(chat, profile, "Hello", LocalDateTime.now());
        Message actual = messageRepository.save(expected);
        assertEquals(expected, actual);
    }
}
