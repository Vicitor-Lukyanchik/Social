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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.BDDMockito.given;
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
    public void save_ShouldThrowException_When() {
        Interest interest = new Interest(1L, "");

        assertThrows(ValidationException.class, () -> validator.validate(interest));
    }

    @Test
    public void save_ShouldSaveInterest() {
        Interest interest = new Interest(1L, "interest");
        given(interestRepository.save(isA(Interest.class))).willReturn(interest);

        interestService.save(interest);

        verify(interestRepository, Mockito.times(1)).save(interest);
    }

    @Test
    public void isExist_ShouldThrowException_WhenInterestNotFound() {
        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.empty());
        boolean actual = interestService.isExist("1l");

        assertEquals(false, actual);
    }

    @Test
    public void isExist_ShouldReturnInterest() {
        Interest interest = new Interest(1l, "23451234");

        given(interestRepository.findByName(isA(String.class))).willReturn(Optional.of(interest));
        boolean actual = interestService.isExist("1l");

        assertEquals(true, actual);
    }

    @Test
    public void findById_ShouldThrowException_WhenInterestNotFound() {
        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.empty());

        assertThrows(ServiceException.class, () -> interestService.findById(1l));
    }

    @Test
    public void findById_ShouldReturnInterest() {
        Interest expected = new Interest(1l, "23451234");

        given(interestRepository.findById(isA(Long.class))).willReturn(Optional.of(expected));
        Interest actual = interestService.findById(1l);

        assertEquals(expected, actual);
    }
}
