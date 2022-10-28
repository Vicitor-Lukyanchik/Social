package com.social.service;

import com.social.entity.Interest;

import javax.validation.Valid;
import java.util.List;

public interface InterestService {

    Interest save(@Valid Interest interest);

    Interest update(Long id, @Valid Interest interest);

    void delete(Long id);

    List<Interest> findAll();

    boolean isExist(String name);

    Interest findById(Long id);
}
