package com.social.service;

import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import com.social.service.exception.ServiceException;
import com.social.validator.BeanValidator;
import com.social.validator.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.social.Constants.ID;
import static com.social.Constants.INTEREST_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterestServiceTest {

    @Autowired
    private InterestService interestService;

    @MockBean
    private InterestRepository interestRepository;

    @Autowired
    private BeanValidator validator;

    @Test
    public void saveShouldThrowExceptionWhenInterestNameEmpty() {
        Interest interest = new Interest(ID, "");

        assertThrows(ValidationException.class, () -> validator.validate(interest));
    }

    @Test
    public void saveShouldSaveInterest() {
        Interest interest = new Interest(ID, INTEREST_NAME);
        given(interestRepository.save(isA(Interest.class))).willReturn(interest);

        interestService.save(interest);

        verify(interestRepository, Mockito.times(1)).save(interest);
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestNotExist() {
        Interest interest = new Interest(ID, INTEREST_NAME);
        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.empty());
        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> interestService.update(interest));
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestWithThisNameExist() {
        Interest interest = new Interest(ID, INTEREST_NAME);
        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.of(interest));
        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.of(interest));

        assertThrows(ServiceException.class, () -> interestService.update(interest));
    }

    @Test
    public void updateShouldUpdateInterest() {
        Interest interest = new Interest(ID, INTEREST_NAME);

        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.of(interest));
        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.empty());
        given(interestRepository.save(isA(Interest.class))).willReturn(interest);

        interestService.update(interest);

        verify(interestRepository, Mockito.times(1)).save(interest);
    }


    @Test
    public void deleteShouldThrowExceptionWhenInterestNotExist() {
        Interest interest = new Interest(ID, INTEREST_NAME);
        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> interestService.update(interest));
    }

    @Test
    public void deleteShouldDeleteInterest() {
        Interest interest = new Interest(ID, INTEREST_NAME);

        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.of(interest));
        doNothing().when(interestRepository).delete(interest);

        interestService.delete(interest);

        verify(interestRepository, Mockito.times(1)).delete(interest);
    }

    @Test
    public void isExistShouldReturnFalseWhenInterestNotFound() {
        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.empty());
        boolean actual = interestService.isExist(INTEREST_NAME);

        assertEquals(false, actual);
    }

    @Test
    public void isExistShouldReturnInterest() {
        Interest interest = new Interest(ID, INTEREST_NAME);

        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.of(interest));
        boolean actual = interestService.isExist(INTEREST_NAME);

        assertEquals(true, actual);
    }

    @Test
    public void findByAllShouldReturnListInterests() {
        List<Interest> expected = Arrays.asList(new Interest(ID, INTEREST_NAME));

        given(interestRepository.findAll()).willReturn(expected);
        List<Interest> actual = interestService.findAll();

        assertEquals(expected.size(), actual.size());
    }

    @Test
    public void findByIdShouldThrowExceptionWhenInterestNotFound() {
        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> interestService.findById(ID));
    }

    @Test
    public void findByIdShouldReturnInterest() {
        Interest expected = new Interest(ID, INTEREST_NAME);

        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        Interest actual = interestService.findById(ID);

        assertEquals(expected, actual);
    }
}
