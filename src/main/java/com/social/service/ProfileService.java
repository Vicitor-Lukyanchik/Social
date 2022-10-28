package com.social.service;

import com.social.entity.*;

import javax.validation.Valid;

public interface ProfileService {

    Profile save(@Valid Profile profile, User user);

    Profile update(Long id, @Valid Profile profile);

    void createChat(Long profileId, Long anotherProfileId, String chatName);

    void joinInGroup(Long profileId, Long groupId);

    Profile findByUserId(Long userId);

    Profile findById(Long id);
}
