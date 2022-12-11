package com.social.service;

import com.social.dto.IndexDto;
import com.social.dto.InterestDto;
import com.social.entity.Interest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface InterestService {

    InterestDto save(InterestDto interestDto);

    InterestDto update(Long id, InterestDto interestDto);

    InterestDto delete(Long id);

    Page<Interest> findAll(IndexDto indexDto);

    boolean isExist(String name);

    Optional<InterestDto> findById(Long id);
}
