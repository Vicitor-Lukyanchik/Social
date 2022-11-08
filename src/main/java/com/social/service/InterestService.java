package com.social.service;

import com.social.dto.InterestDto;
import com.social.entity.Interest;

import java.util.List;

public interface InterestService {

    InterestDto save(InterestDto interestDto);

    InterestDto update(Long id, InterestDto interestDto);

    InterestDto delete(Long id);

    List<Interest> findAll();

    boolean isExist(String name);

    InterestDto findById(Long id);
}
