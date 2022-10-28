package com.social.repository;

import com.social.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static com.social.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void saveShouldSavePost() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).build());
        Interest interest = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());
        Group group = groupRepository.save(Group.builder().name(GROUP_NAME).profile(profile).interest(interest).build());
        Chat chat = chatRepository.save(Chat.builder().name(CHAT_NAME).messages(new ArrayList<>()).build());

        Post expected = Post.builder().group(group).chat(chat).title(POST_TITLE)
                .text(POST_TEXT).dateTime(LocalDateTime.now()).build();
        Post actual = postRepository.save(expected);
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getChat(), actual.getChat());
        assertEquals(expected.getGroup(), actual.getGroup());
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }
}
