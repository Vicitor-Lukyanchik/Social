package com.social.service;

import com.social.entity.Interest;
import com.social.service.exception.ServiceException;

import java.util.List;

public interface InterestService {

    Interest save(Interest interest) throws ServiceException;

    Interest update(Long id, Interest interest) throws ServiceException;

    void delete(Long id) throws ServiceException;

    List<Interest> findAll();

    boolean isExist(String name);

    Interest findById(Long id) throws ServiceException;
}
