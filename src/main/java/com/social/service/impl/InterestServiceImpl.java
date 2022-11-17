package com.social.service.impl;

import com.social.converter.InterestToDtoConverter;
import com.social.converter.DtoToInterestConverter;
import com.social.dto.InterestDto;
import com.social.dto.InterestIndexDto;
import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.InterestService;
import com.social.specification.InterestSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

import static com.social.specification.InterestSpecifications.sortAsc;

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
    public Page<Interest> findAll(InterestIndexDto interestIndexDto) {
        if (interestIndexDto.isSort()) {
            return findAllWithPaginationAndSorting(interestIndexDto.getOffset(), interestIndexDto.getPageSize());
        }
        return findAllWithPagination(interestIndexDto.getOffset(), interestIndexDto.getPageSize());
    }

    private Page<Interest> findAllWithPagination(int offset, int pageSize){
        return interestRepository.findAll(PageRequest.of(offset, pageSize));
    }

    private Page<Interest> findAllWithPaginationAndSorting(int offset, int pageSize){
        return interestRepository.findAll(PageRequest.of(offset, pageSize, Sort.by("name")));
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

    @Override
    public InterestDto findByName(String name) {
        return toDtoConverter.convert(interestRepository.findAll(InterestSpecifications.findByName(name)).get(0));
    }
}
