package com.social.service;

import com.social.entity.*;
import com.social.service.exception.ServiceException;

import javax.validation.Valid;

public interface ProfileService {

    Profile save(Profile profile, User user);

    Profile update(Long id, Profile profile) throws ServiceException;

    void createChat(Long profileId, Long anotherProfileId, String chatName) throws ServiceException;

    void joinInGroup(Long profileId, Long groupId) throws ServiceException;

    Profile findByUserId(Long userId) throws ServiceException;

    Profile findById(Long id) throws ServiceException;
}
