package com.social.repository;

import com.social.entity.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.social.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void test() {
        assertThat(groupRepository).isNotNull();
    }

    @Test
    public void saveShouldSaveGroup() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD)
                .roles(Collections.EMPTY_LIST).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).joinGroups(Collections.EMPTY_LIST)
                .createdGroups(Collections.EMPTY_LIST).chats(Collections.EMPTY_LIST).build());
        Interest interest = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());

        Group expected = groupRepository.save(Group.builder().name(GROUP_NAME).profile(profile).interest(interest).build());
        Group actual = groupRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getInterest(), actual.getInterest());
    }

}
