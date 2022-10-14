package com.social.service;

import com.social.entity.Profile;
import com.social.entity.User;

public interface UserService {
    User register(User user, Profile profile);

    User findByUsername(String username);

    User findById(Long id);
}
