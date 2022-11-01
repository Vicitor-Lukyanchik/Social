package com.social.repository;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;
import com.social.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.social.Constants.*;
import static com.social.util.MockUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @AfterEach
    public void cleanUp(){
        messageRepository.deleteAll();
        messageRepository.flush();

        chatRepository.deleteAll();
        chatRepository.flush();

        profileRepository.deleteAll();
        profileRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void saveShouldSaveMessage() {
        Chat chat = chatRepository.save(createChat());
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));

        Message expected = createMessage(profile, chat);

        Message actual = messageRepository.save(expected);
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getChat(), actual.getChat());
        assertEquals(expected.getProfile(), actual.getProfile());
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }
}
