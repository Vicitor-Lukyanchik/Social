package com.social.repository;

import com.social.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static com.social.Constants.*;
import static com.social.util.MockUtils.*;
import static com.social.util.MockUtils.createGroup;
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

    @AfterEach
    public void cleanUp(){
        postRepository.deleteAll();
        postRepository.flush();

        chatRepository.deleteAll();
        chatRepository.flush();

        groupRepository.deleteAll();
        groupRepository.flush();

        interestRepository.deleteAll();
        interestRepository.flush();

        profileRepository.deleteAll();
        profileRepository.flush();

        userRepository.deleteAll();
        userRepository.flush();
    }

    @Test
    public void saveShouldSavePost() {
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Interest interest = interestRepository.save(createInterest());

        Group group = groupRepository.save(createGroup(profile, interest));
        Chat chat = chatRepository.save(createChat());

        Post expected = createPost(group, chat);
        Post actual = postRepository.save(expected);
        assertEquals(expected.getText(), actual.getText());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getChat(), actual.getChat());
        assertEquals(expected.getGroup(), actual.getGroup());
        assertEquals(expected.getDateTime(), actual.getDateTime());
    }
}
