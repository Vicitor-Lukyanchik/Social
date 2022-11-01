package com.social.repository;

import com.social.entity.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.social.Constants.*;
import static com.social.util.MockUtils.*;
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

    @AfterEach
    public void cleanUp(){
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
    public void saveShouldSaveGroup() {
        User user = userRepository.save(createUser());
        Profile profile = profileRepository.save(createProfile(user));
        Interest interest = interestRepository.save(createInterest());

        Group expected = groupRepository.save(createGroup(profile, interest));
        Group actual = groupRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getInterest(), actual.getInterest());
    }

}
