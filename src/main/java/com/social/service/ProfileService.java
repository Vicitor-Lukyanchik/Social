package com.social.service;

import com.social.entity.*;

public interface ProfileService {

    Profile save(Profile profile, User user);

    Profile update(Profile profile);

    void createChat(Profile profile, Profile anotherProfile, String chatName);

    void joinInGroup(Profile profile, Group group);

    Profile findByUser(User user);

    Profile findById(Long id);
}
