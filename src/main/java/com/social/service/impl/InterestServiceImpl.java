package com.social.service.impl;

import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.InterestService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public Interest save(@Valid Interest interest) {
        if (isExist(interest.getName())) {
            throw new ServiceException("Interest have been created with name " + interest.getName());
        }
        Interest result = interestRepository.save(interest);
        return result;
    }

    @Override
    public Interest update(Long id, @Valid Interest updatedInterest) {
        isPresent(id);
        if (isExist(updatedInterest.getName())) {
            throw new ServiceException("Interest have been created with name " + updatedInterest.getName());
        }
        Interest interest = interestRepository.findById(id).get();
        interest.setName(updatedInterest.getName());
        return interestRepository.save(updatedInterest);
    }


    @Override
    public void delete(Long id) {
        isPresent(id);
        interestRepository.deleteById(id);
    }

    private void isPresent(Long id) {
        if (!interestRepository.findById(id).isPresent()) {
            throw new ServiceException("Interest haven't been founded by id : " + id);
        }
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
    public Interest findById(Long id) {
        Optional<Interest> interest = interestRepository.findById(id);

        if (interest.isEmpty()) {
            throw new ServiceException("Interest haven't been founded by id : " + id);
        }
        return interest.get();
    }
}
