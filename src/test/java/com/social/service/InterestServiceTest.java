package com.social.service;

import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.exception.ServiceException;
import com.social.validator.BeanValidator;
import com.social.validator.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static com.social.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InterestServiceTest {

    @Autowired
    private InterestService interestService;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private BeanValidator validator;

    @AfterEach
    public void cleanUp(){
        interestRepository.deleteAll();
        interestRepository.flush();
    }

    @Test
    public void saveShouldThrowExceptionWhenInterestNameEmpty() {
        Interest interest = Interest.builder().name(EMPTY_STRING).build();

        assertThrows(ValidationException.class, () -> validator.validate(interest));
    }

    @Test
    public void saveShouldSaveInterest() throws ServiceException {
        Interest expected = Interest.builder().name(INTEREST_NAME).build();

        Interest actual = interestService.save(expected);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestNotExist() {
        Interest interest = Interest.builder().name(INTEREST_NAME).id(ID).build();

        assertThrows(ServiceException.class, () -> interestService.update(ID, interest));
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestWithThisNameExist() {
        interestRepository.save(Interest.builder().name(INTEREST_NAME).build());
        Interest interest = new Interest(ID, INTEREST_NAME);

        assertThrows(ServiceException.class, () -> interestService.update(ID, interest));
    }

    @Test
    public void updateShouldUpdateInterest() throws ServiceException {
        Interest interest = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());
        Interest expected = Interest.builder().name(ANOTHER_INTEREST_NAME).build();

        Interest actual = interestService.update(interest.getId(), expected);

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
    }


    @Test
    public void deleteShouldThrowExceptionWhenInterestNotExist() {
        assertThrows(ServiceException.class, () -> interestService.delete(ID));
    }

    @Test
    public void deleteShouldDeleteInterest() throws ServiceException {
        Interest interest = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());

        interestService.delete(interest.getId());

        assertTrue(interestRepository.findAll().isEmpty());
    }

    @Test
    public void isExistShouldReturnFalseWhenInterestNotFound() {
        assertFalse(interestService.isExist(INTEREST_NAME));
    }

    @Test
    public void isExistShouldReturnInterest() {
        interestRepository.save(Interest.builder().name(INTEREST_NAME).build());

        assertTrue(interestService.isExist(INTEREST_NAME));
    }

    @Test
    public void findByAllShouldReturnListInterests() {
        Interest interest = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());
        List<Interest> expected = Arrays.asList(interest);

        List<Interest> actual = interestService.findAll();

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenInterestNotFound() {
        assertThrows(ServiceException.class, () -> interestService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnInterest() throws ServiceException {
        Interest expected = interestRepository.save(Interest.builder().name(INTEREST_NAME).build());

        Interest actual = interestService.findById(expected.getId());

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getId(), actual.getId());
    }
}
