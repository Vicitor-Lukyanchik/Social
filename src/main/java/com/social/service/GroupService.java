package com.social.service;

import com.social.entity.Group;
import com.social.entity.Interest;
import com.social.entity.Profile;
import com.social.exception.ServiceException;

public interface GroupService {

    Group save(Group group, Profile profile, Interest interest) throws ServiceException;

    boolean isPresent(Long id);

    Group findById(Long id) throws ServiceException;
}
