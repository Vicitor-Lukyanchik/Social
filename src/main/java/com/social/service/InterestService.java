package com.social.service;

import com.social.entity.Interest;

public interface InterestService {

    Interest save(Interest interest);

    boolean isExist(String name);

    Interest findById(Long id);
}
