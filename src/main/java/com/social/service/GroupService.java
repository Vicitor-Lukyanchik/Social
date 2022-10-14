package com.social.service;

import com.social.entity.Group;
import com.social.entity.Interest;
import com.social.entity.Profile;

public interface GroupService {

    Group save(Group group, Profile profile, Interest interest);

    boolean isExist(Group group);

    Group findById(Long id);
}
