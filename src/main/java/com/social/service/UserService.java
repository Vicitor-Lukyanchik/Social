package com.social.service;

import com.social.entity.Profile;
import com.social.entity.User;

import javax.validation.Valid;

public interface UserService {
    User register(@Valid User user, @Valid Profile profile);

    User findByUsername(String username);

    User findById(Long id);
}
