package com.social.service.impl;

import com.social.converter.InterestToDtoConverter;
import com.social.converter.DtoToInterestConverter;
import com.social.dto.InterestDto;
import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;
    private final InterestToDtoConverter toDtoConverter;
    private final DtoToInterestConverter dtoToInterestConverter;

    @Override
    public InterestDto save(InterestDto interestDto) {
        if (isExist(interestDto.getName())) {
            return InterestDto.builder()
                    .message("Interest have been created with name " + interestDto.getName())
                    .build();
        }
        Interest result = interestRepository.save(dtoToInterestConverter.convert(interestDto));
        return toDtoConverter.convert(result);
    }

    @Override
    public InterestDto update(Long id, InterestDto updatedInterestDto) {
        if (!isPresent(id)) {
            return InterestDto.builder()
                    .message("Interest haven't been founded by id : " + id)
                    .build();
        }
        if (isExist(updatedInterestDto.getName())) {
            return InterestDto.builder()
                    .message("Interest have been founded with name " + updatedInterestDto.getName())
                    .build();
        }
        Interest interest = interestRepository.findById(id).get();
        interest.setName(updatedInterestDto.getName());
        return toDtoConverter.convert(interestRepository.save(interest));
    }

    @Override
    public InterestDto delete(Long id) {
        if (!isPresent(id)) {
            return InterestDto.builder()
                    .message("Interest haven't been founded by id : " + id)
                    .build();
        }
        interestRepository.deleteById(id);
        return InterestDto.builder().build();
    }

    private boolean isPresent(Long id) {
        return interestRepository.findById(id).isPresent();
    }

    @Override
    public List<Interest> findAll() {
        return interestRepository.findAll();
    }

    @Override
    public boolean isExist(String name) {
        return interestRepository.findByName(name).isPresent();
    }

    @Override
    public InterestDto findById(Long id) {
        Optional<Interest> interest = interestRepository.findById(id);
        if (interest.isEmpty()) {
            return InterestDto.builder().message("Interest haven't been founded by id : " + id).build();
        }
        return toDtoConverter.convert(interest.get());
    }
}
