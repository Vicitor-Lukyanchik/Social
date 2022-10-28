package com.social.repository;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;
import com.social.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.social.Constants.*;
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

    @Test
    public void test() {
        assertThat(messageRepository).isNotNull();
    }

    @Test
    public void saveShouldSaveMessage() {
        Chat chat = chatRepository.save(Chat.builder().name(CHAT_NAME).messages(new ArrayList<>()).build());
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).build());

        Message expected = Message.builder().chat(chat).profile(profile)
                .text(MESSAGE_TEXT).dateTime(LocalDateTime.now()).build();

        Message actual = messageRepository.save(expected);
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getChat(), actual.getChat());
        assertEquals(expected.getProfile(), actual.getProfile());
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }
}
