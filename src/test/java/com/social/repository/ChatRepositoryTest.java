package com.social.repository;

import com.social.entity.Chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.social.Constants.CHAT_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void test() {
        assertThat(chatRepository).isNotNull();
    }

    @Test
    public void saveShouldSaveChat() {
        Chat expected = Chat.builder().name(CHAT_NAME).build();
        Chat actual = chatRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
    }
}
