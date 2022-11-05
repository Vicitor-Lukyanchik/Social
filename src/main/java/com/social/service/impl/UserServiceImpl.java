package com.social.service.impl;

import com.social.converter.RegistrationRequestToProfileConverter;
import com.social.converter.RegistrationRequestToUserConverter;
import com.social.dto.authentication.AuthenticationRequestDto;
import com.social.dto.authentication.RegistrationRequestDto;
import com.social.entity.Profile;
import com.social.entity.Role;
import com.social.entity.Status;
import com.social.entity.User;
import com.social.exception.PasswordRepeatException;
import com.social.repository.UserRepository;
import com.social.security.jwt.JwtTokenProvider;
import com.social.service.ProfileService;
import com.social.service.RoleService;
import com.social.service.UserService;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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

    @Override
    public String login(AuthenticationRequestDto requestDto, RedirectAttributes redirectAttributes) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = findByUsername(username);
            jwtTokenProvider.createToken(username, user.getRoles());
            return "redirect:/profiles/" + user.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isNotLogin", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/login";
        }
    }

    @Override
    @Transactional
    public String register(RegistrationRequestDto requestDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        try {
            if (!requestDto.getPassword().equals(requestDto.getRepeatedPassword())) {
                throw new PasswordRepeatException("Password should repeat correct");
            }

            User registeredUser = registerUser(requestToUserConverter.convert(requestDto),
                    requestToProfileConverter.convert(requestDto));
            jwtTokenProvider.createToken(registeredUser.getUsername(), registeredUser.getRoles());
            return "redirect:/profiles/" + registeredUser.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("isNotRegister", true);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/auth/register";
        }
    }

    @Override
    @Transactional
    public User registerUser(User user, Profile profile) throws ServiceException {
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
