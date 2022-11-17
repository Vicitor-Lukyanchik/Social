package com.social.service;

import com.social.dto.InterestDto;
import com.social.dto.InterestIndexDto;
import com.social.entity.Interest;
import org.springframework.data.domain.Page;

public interface InterestService {

    InterestDto save(InterestDto interestDto);

    InterestDto update(Long id, InterestDto interestDto);

    InterestDto delete(Long id);

    Page<Interest> findAll(InterestIndexDto interestIndexDto);

    boolean isExist(String name);

    InterestDto findById(Long id);

    InterestDto findByName(String name);
}
