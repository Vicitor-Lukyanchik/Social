package com.social.service;

import com.social.entity.*;

import javax.validation.Valid;

public interface ProfileService {

    Profile save(@Valid Profile profile, User user);

    Profile update(@Valid Profile profile);

    void createChat(Profile profile, Profile anotherProfile, String chatName);

    void joinInGroup(Profile profile, Group group);

    Profile findByUser(User user);

    Profile findById(Long id);
}
