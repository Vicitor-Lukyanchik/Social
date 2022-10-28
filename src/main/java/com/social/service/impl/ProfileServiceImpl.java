package com.social.service.impl;

import com.social.entity.*;
import com.social.repository.ProfileRepository;
import com.social.service.ChatService;
import com.social.service.GroupService;
import com.social.service.ProfileService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ProfileServiceImpl implements ProfileService {

    private static final String NOT_SPECIFIED = "not specified";
    private static final String CHAT_NAME_DELIMITER = " | ";

    private final ProfileRepository profileRepository;
    private final ChatService chatService;
    private final GroupService groupService;

    @Override
    @Transactional
    public Profile save(@Valid Profile profile, User user) {
        Profile result = profileRepository.save(buildProfile(profile, user));
        return result;
    }

    private Profile buildProfile(Profile profile, User user) {
        profile.setUser(user);
        profile.setSex(Sex.UNDEFINED);
        profile.setFamilyStatus(NOT_SPECIFIED);
        profile.setTown(NOT_SPECIFIED);
        profile.setPhone(NOT_SPECIFIED);
        profile.setCreatedGroups(new ArrayList<>());
        profile.setJoinGroups(new ArrayList<>());
        profile.setChats(new ArrayList<>());
        return profile;
    }

    @Override
    @Transactional
    public Profile update(@Valid Profile profile) {
        Profile result = profileRepository.save(profile);
        return result;
    }

    @Override
    @Transactional
    public void createChat(Profile profile, Profile anotherProfile, String chatName) {
        checkProfiles(profile, anotherProfile);
        chatName = createChatName(profile, anotherProfile, chatName);
        chatService.save(new Chat(chatName, Arrays.asList(profile, anotherProfile)));
    }

    private void checkProfiles(Profile profile, Profile anotherProfile) {
        if (!isExist(anotherProfile)) {
            throw new ServiceException("Profile haven't been founded by id " + anotherProfile.getId());
        }
        if (!isExist(profile)) {
            throw new ServiceException("Profile haven't been founded by id " + profile.getId());
        }
    }

    private String createChatName(Profile profile, Profile anotherProfile, String chatName) {
        if (chatName.isEmpty()){
            chatName = profile.getFirstname() + CHAT_NAME_DELIMITER + anotherProfile.getFirstname();
        }
        return chatName;
    }

    @Override
    @Transactional
    public void joinInGroup(Profile profile, Group group) {
        if (!isExist(profile)) {
            throw new ServiceException("Profile haven't been founded by id " + profile.getId());
        }
        if (!groupService.isExist(group)) {
            throw new ServiceException("Group haven't been founded by id : " + group.getId());
        }
        profile.getJoinGroups().add(group);
        update(profile);
    }

    private boolean isExist(Profile profile) {
        try {
            findById(profile.getId());
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Profile findByUser(User user) {
        Optional<Profile> profile = profileRepository.findByUser(user);

        if (profile.isEmpty()) {
            throw new ServiceException("Profile haven't been founded by user id: " + user.getId());
        }
        return profile.get();
    }

    @Override
    @Transactional
    public Profile findById(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);

        if (profile.isEmpty()) {
            throw new ServiceException("Profile haven't been founded by id : " + id);
        }
        return profile.get();
    }
}
