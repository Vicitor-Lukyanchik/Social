package com.social.service;

import com.social.dto.ProfileDto;
import com.social.entity.*;
import com.social.exception.ServiceException;

import javax.validation.Valid;

public interface ProfileService {

    Profile save(Profile profile, User user);

    ProfileDto update(Long id, @Valid ProfileDto profile);

    void createChat(Long profileId, Long anotherProfileId, String chatName) throws ServiceException;

    void joinInGroup(Long profileId, Long groupId) throws ServiceException;

    ProfileDto findByUserId(Long userId);

    ProfileDto findById(Long id);
}
