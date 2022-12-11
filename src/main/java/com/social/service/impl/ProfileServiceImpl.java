package com.social.service.impl;

import com.social.converter.DtoToProfileConverter;
import com.social.converter.ProfileToDtoConverter;
import com.social.dto.ProfileDto;
import com.social.entity.*;
import com.social.repository.ProfileRepository;
import com.social.service.ChatService;
import com.social.service.GroupService;
import com.social.service.ProfileService;
import com.social.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class ProfileServiceImpl implements ProfileService {

    private static final String NOT_SPECIFIED = "Undefined";
    private static final String CHAT_NAME_DELIMITER = " | ";

    private final ProfileRepository profileRepository;
    private final ChatService chatService;
    private final GroupService groupService;
    private final ProfileToDtoConverter profileToDtoConverter;
    private final DtoToProfileConverter dtoToProfileConverter;

    @Override
    @Transactional
    public Profile save(Profile profile, User user) {
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
    public ProfileDto update(Long id, @Valid ProfileDto updatedProfile) {
        if (!isPresent(id)) {
            return ProfileDto.builder().message("Profile haven't been founded by id : " + id).build();
        }
        Profile profile = profileRepository.findById(id).get();
        profile.setFirstname(updatedProfile.getFirstname());
        profile.setLastname(updatedProfile.getLastname());
        profile.setAge(updatedProfile.getAge());
        profile.setEmail(updatedProfile.getEmail());
        profile.setSex(updatedProfile.getSex());
        profile.setFamilyStatus(updatedProfile.getFamilyStatus());
        profile.setTown(updatedProfile.getTown());
        profile.setPhone(updatedProfile.getPhone());
        Profile save = profileRepository.save(profile);
        ProfileDto convert = profileToDtoConverter.convert(save);
        return convert;
    }

    @Override
    public void createChat(Long profileId, Long anotherProfileId, String chatName) throws ServiceException {
        checkProfiles(profileId, anotherProfileId);
        Profile profile = dtoToProfileConverter.convert(findById(profileId).get());
        Profile anotherProfile = dtoToProfileConverter.convert(findById(anotherProfileId).get());
        chatName = createChatName(profile, anotherProfile, chatName);
        chatService.save(Chat.builder().name(chatName).profiles(Arrays.asList(profile, anotherProfile)).build());
    }

    private void checkProfiles(Long profileId, Long anotherProfileId) {
        isPresent(profileId);
        isPresent(anotherProfileId);
    }

    private String createChatName(Profile profile, Profile anotherProfile, String chatName) {
        if (chatName.isEmpty()) {
            chatName = profile.getFirstname() + CHAT_NAME_DELIMITER + anotherProfile.getFirstname();
        }
        return chatName;
    }

    @Override
    public void joinInGroup(Long profileId, Long groupId) throws ServiceException {
        isPresent(profileId);
        if (!groupService.isPresent(groupId)) {
            throw new ServiceException("Group haven't been founded by id : " + groupId);
        }
        Profile profile = profileRepository.findById(profileId).get();
        Group group = groupService.findById(groupId);
        profile.getJoinGroups().add(group);
        profileRepository.save(profile);
    }


    private boolean isPresent(Long id) {
        return profileRepository.existsById(id);
    }

    @Override
    public ProfileDto findByUserId(Long userId) {
        Optional<Profile> result = profileRepository.findProfileByUserId(userId);
        if (!result.isPresent()) {
            return ProfileDto.builder().message("Profile haven't been founded by user id: " + userId).build();
        }
        return profileToDtoConverter.convert(result.get());
    }

    @Override
    public Optional<ProfileDto> findById(Long id) {
        Optional<Profile> result = profileRepository.findById(id);
        if (!result.isPresent()) {
            log.warn("Profile haven't been founded by id : " + id);
            return Optional.empty();
        }
        return Optional.of(profileToDtoConverter.convert(result.get()));
    }
}
