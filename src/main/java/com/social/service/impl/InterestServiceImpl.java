package com.social.service.impl;

import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.InterestService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class InterestServiceImpl implements InterestService {

    private final InterestRepository interestRepository;

    @Override
    @Transactional
    public Interest save(@Valid Interest interest) {
        if (isExist(interest.getName())){
            throw new ServiceException("Interest have been created with name " + interest.getName());
        }
        Interest result = interestRepository.save(interest);
        return result;
    }

    @Override
    @Transactional
    public Interest update(@Valid Interest interest) {
        findById(interest.getId());
        if (isExist(interest.getName())){
            throw new ServiceException("Interest have been created with name " + interest.getName());
        }
        return interestRepository.save(interest);
    }

    @Override
    @Transactional
    public void delete(Interest interest) {
        findById(interest.getId());
        interestRepository.delete(interest);
    }

    @Override
    @Transactional
    public List<Interest> findAll() {
        return interestRepository.findAll();
    }

    @Override
    @Transactional
    public boolean isExist(String name) {
        Optional<Interest> interest = interestRepository.findByName(name);
        if (interest.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Interest findById(Long id) {
        Optional<Interest> interest = interestRepository.findById(id);

        if (interest.isEmpty()) {
            throw new ServiceException("Interest haven't been founded by id : " + id);
        }
        return interest.get();
    }
}
