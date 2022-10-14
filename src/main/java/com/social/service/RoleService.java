package com.social.service;

import com.social.entity.Role;

import java.util.List;

public interface RoleService {

    List<Role> findByName(String name);
}
