package com.social.service;

import com.social.entity.Role;
import com.social.service.exception.ServiceException;

import java.util.List;

public interface RoleService {

    List<Role> findByName(String name) throws ServiceException;
}
