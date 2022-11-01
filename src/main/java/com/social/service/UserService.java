package com.social.service;

import com.social.entity.Profile;
import com.social.entity.User;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;

import javax.validation.Valid;

public interface UserService {
    User register(User user, Profile profile) throws ServiceException;

    User findByUsername(String username) throws UserNotFoundException;

    User findById(Long id) throws UserNotFoundException;
}
