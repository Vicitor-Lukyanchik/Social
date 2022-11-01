package com.social.service.impl;

import com.social.entity.Role;
import com.social.entity.User;
import com.social.repository.RoleRepository;
import com.social.service.RoleService;
import com.social.service.exception.ServiceException;
import com.social.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Validated
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<Role> findByName(String name) throws ServiceException {
        List<Role> result = roleRepository.findByName(name);

        if (result.isEmpty()) {
            throw new ServiceException("Role haven't founded by name : " + name);
        }
        return result;
    }
}
