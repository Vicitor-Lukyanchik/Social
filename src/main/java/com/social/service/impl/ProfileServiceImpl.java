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

    private static final String NOT_SPECIFIED = "Undefined";
    private static final String CHAT_NAME_DELIMITER = " | ";

    private final ProfileRepository profileRepository;
    private final ChatService chatService;
    private final GroupService groupService;

    @Override
    @Transactional
    public Profile save(@Valid Profile profile, User user) {
        return profileRepository.save(buildProfile(profile, user));
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
    public Profile update(Long id, @Valid Profile updatedProfile) {
        isPresent(id);
        Profile profile = profileRepository.findById(id).get();
        if (updatedProfile.getUser().equals(profile.getUser())) {
            throw new ServiceException("User id shouldn't be updated ");
        }
           profile.setFirstname(updatedProfile.getFirstname());
            profile.setLastname(updatedProfile.getLastname());
                 profile.setAge(updatedProfile.getAge());
               profile.setEmail(updatedProfile.getEmail());
                 profile.setSex(updatedProfile.getSex());
        profile.setFamilyStatus(updatedProfile.getFamilyStatus());
                profile.setTown(updatedProfile.getTown());
               profile.setPhone(updatedProfile.getPhone());
        return profileRepository.save(updatedProfile);
    }

    @Override
    public void createChat(Long profileId, Long anotherProfileId, String chatName) {
        checkProfiles(profileId, anotherProfileId);
        Profile profile = findById(profileId);
        Profile anotherProfile = findById(anotherProfileId);
        chatName = createChatName(profile, anotherProfile, chatName);
        chatService.save(Chat.builder().name(chatName).profiles(Arrays.asList(profile, anotherProfile)).build());
    }

    private void checkProfiles(Long profileId, Long anotherProfileId) {
        isPresent(profileId);
        isPresent(anotherProfileId);
    }

    private String createChatName(Profile profile, Profile anotherProfile, String chatName) {
        if (chatName.isEmpty()){
            chatName = profile.getFirstname() + CHAT_NAME_DELIMITER + anotherProfile.getFirstname();
        }
        return chatName;
    }

    @Override
    public void joinInGroup(Long profileId, Long groupId) {
        isPresent(profileId);
        if (!groupService.isExist(groupId)) {
            throw new ServiceException("Group haven't been founded by id : " + groupId);
        }
        Profile profile = profileRepository.findById(profileId).get();
        Group group = groupService.findById(groupId);
        profile.getJoinGroups().add(group);
        update(profileId, profile);
    }


    private void isPresent(Long id) {
        if (!profileRepository.findById(id).isPresent()) {
            throw new ServiceException("Profile haven't been founded by id : " + id);
        }
    }

    @Override
    public Profile findByUserId(Long userId) {
        Optional<Profile> profile = profileRepository.findProfileByUserId(userId);

        if (profile.isEmpty()) {
            throw new ServiceException("Profile haven't been founded by user id: " + userId);
        }
        return profile.get();
    }

    @Override
    public Profile findById(Long id) {
        Optional<Profile> profile = profileRepository.findById(id);

        if (profile.isEmpty()) {
            throw new ServiceException("Profile haven't been founded by id : " + id);
        }
        return profile.get();
    }
}
