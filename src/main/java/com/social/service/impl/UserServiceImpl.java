package com.social.service.impl;

import com.social.entity.Profile;
import com.social.entity.Role;
import com.social.entity.Status;
import com.social.entity.User;
import com.social.repository.UserRepository;
import com.social.service.ProfileService;
import com.social.service.RoleService;
import com.social.service.UserService;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class UserServiceImpl implements UserService {

    private static final String ROLE_USER = "ROLE_USER";

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProfileService profileService;

    @Override
    @Transactional
    public User register(@Valid User user, @Valid Profile profile) {
        if (isUsernameUsed(user.getUsername())) {
            throw new ServiceException("Username is already exists");
        }

        User registeredUser = userRepository.save(buildUser(user));
        profileService.save(profile, user);

        return registeredUser;
    }

    private User buildUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRoles = roleService.findByName(ROLE_USER);
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        return user;
    }

    private boolean isUsernameUsed(String username) {
        try {
            findByUsername(username);
            return true;
        } catch (UserNotFoundException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);

        if (result.isEmpty()) {
            throw new UserNotFoundException("User haven't founded by username : " + username);
        }
        return result.get();
    }

    @Override
    @Transactional
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User haven't been founded by id : " + id);
        }
        return user.get();
    }
}
