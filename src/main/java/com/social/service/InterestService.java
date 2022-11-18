package com.social.service;

import com.social.dto.InterestDto;
import com.social.dto.InterestIndexDto;
import com.social.entity.Interest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface InterestService {

    InterestDto save(InterestDto interestDto);

    InterestDto update(Long id, InterestDto interestDto);

    InterestDto delete(Long id);

    Page<Interest> findAll(Optional<Integer> offset, Optional<Integer> pageSize, boolean isSort);

    boolean isExist(String name);

    InterestDto findById(Long id);

    InterestDto findByName(String name);
}
