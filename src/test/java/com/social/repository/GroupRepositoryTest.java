package com.social.repository;

import com.social.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.social.Constants.*;
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
    public void saveShouldSaveGroup() {
        User user = userRepository.save(User.builder().username(USERNAME).password(PASSWORD).status(STATUS).build());
        Profile profile = profileRepository.save(Profile.builder()
                .firstname(FIRSTNAME)
                .lastname(LASTNAME).email(EMAIL)
                .sex(SEX).age(AGE).user(user).build());
        Interest interest = interestRepository.save(new Interest(INTEREST_NAME));

        Group expected = groupRepository.save(Group.builder().name(GROUP_NAME).profile(profile).interest(interest).build());
        Group actual = groupRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
    }
}
