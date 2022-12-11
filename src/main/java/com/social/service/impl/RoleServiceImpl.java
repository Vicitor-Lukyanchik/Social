package com.social.service.impl;

import com.social.entity.Role;
import com.social.repository.RoleRepository;
import com.social.service.RoleService;
import com.social.exception.ServiceException;
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
    public List<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
