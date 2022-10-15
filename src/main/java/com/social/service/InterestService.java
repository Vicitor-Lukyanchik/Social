package com.social.service;

import com.social.entity.Interest;

import javax.validation.Valid;

public interface InterestService {

    Interest save(@Valid Interest interest);

    boolean isExist(String name);

    Interest findById(Long id);
}
