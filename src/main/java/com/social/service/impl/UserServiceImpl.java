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
    public User register(User user, Profile profile) throws ServiceException {
        if (isUsernameUsed(user.getUsername())) {
            throw new ServiceException("Username is already exists");
        }

        User registeredUser = userRepository.save(buildUser(user));
        profileService.save(profile, user);

        return registeredUser;
    }

    private User buildUser(User user) throws ServiceException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<Role> userRoles = roleService.findByName(ROLE_USER);
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        return user;
    }

    private boolean isUsernameUsed(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        User result = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User haven't founded by username : " + username));
        return result;
    }

    @Override
    public User findById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User haven't been founded by id : " + id));
        return user;
    }
}
