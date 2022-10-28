package com.social.repository;

import com.social.entity.Chat;
import com.social.entity.Interest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static com.social.Constants.CHAT_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
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
        Chat expected = new Chat(CHAT_NAME, new ArrayList<>());
        Chat actual = chatRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
    }
}
