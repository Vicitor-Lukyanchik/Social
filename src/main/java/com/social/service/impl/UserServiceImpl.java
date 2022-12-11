package com.social.service.impl;

import com.social.converter.RegistrationRequestToProfileConverter;
import com.social.converter.RegistrationRequestToUserConverter;
import com.social.converter.UserToDtoConverter;
import com.social.dto.UserDto;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.Profile;
import com.social.entity.Role;
import com.social.entity.Status;
import com.social.entity.User;
import com.social.repository.UserRepository;
import com.social.jwt.JwtTokenProvider;
import com.social.service.ProfileService;
import com.social.service.RoleService;
import com.social.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
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
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegistrationRequestToUserConverter requestToUserConverter;
    private final RegistrationRequestToProfileConverter requestToProfileConverter;
    private final UserToDtoConverter userToDtoConverter;

    @Override
    @Transactional
    public UserDto login(AuthenticationRequestDto requestDto) {
            String username = requestDto.getUsername();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        } catch (AuthenticationException e) {
            return UserDto.builder().message("Invalid password").build();
        }
        UserDto userDto = findByUsername(username);
            jwtTokenProvider.createToken(username, userDto.getRoles());
            return userDto;
    }

    @Override
    @Transactional
    public UserDto register(RegistrationRequestDto requestDto) {
        if (!requestDto.getPassword().equals(requestDto.getRepeatedPassword())) {
            return UserDto.builder().message("Password should repeat correct").build();
        }
            UserDto registeredUser = registerUser(requestToUserConverter.convert(requestDto),
                    requestToProfileConverter.convert(requestDto));
            jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getRoles());
            return registeredUser;
    }

    @Override
    @Transactional
    public UserDto registerUser(@Valid User user, @Valid Profile profile) {
        if (isUsernameUsed(user.getUsername())) {
            return UserDto.builder().message("Username is already exists").build();
        }

        User registeredUser = userRepository.save(buildUser(user));
        profileService.save(profile, user);

        return userToDtoConverter.convert(registeredUser);
    }

    private User buildUser(User user) {
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
    public UserDto findByUsername(String username) {
        Optional<User> result = userRepository.findByUsername(username);
        if (!result.isPresent()){
            return UserDto.builder().message("User haven't founded by username : " + username).build();
        }
        return userToDtoConverter.convert(result.get());
    }

    @Override
    public UserDto findById(Long id) {
        Optional<User> result = userRepository.findById(id);
        if (!result.isPresent()){
            return UserDto.builder().message("User haven't founded by id : " + id).build();
        }
        return userToDtoConverter.convert(result.get());
    }
}
