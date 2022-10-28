package com.social.service;

import com.social.entity.Group;
import com.social.entity.Interest;
import com.social.entity.Profile;

import javax.validation.Valid;

public interface GroupService {

    Group save(@Valid Group group, Profile profile, Interest interest);

    boolean isExist(Group group);

    Group findById(Long id);
}
