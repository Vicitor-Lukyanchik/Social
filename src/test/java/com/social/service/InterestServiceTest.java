package com.social.service;

import com.social.converter.DtoToInterestConverter;
import com.social.converter.InterestToDtoConverter;
import com.social.dto.InterestDto;
import com.social.entity.Interest;
import com.social.repository.InterestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static com.social.Constants.*;
import static com.social.util.MockUtils.createInterestDto;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InterestServiceTest {

    @Autowired
    private InterestService interestService;

    @Autowired
    private InterestToDtoConverter interestToDtoConverter;

    @Autowired
    private DtoToInterestConverter dtoToInterestConverter;

    @Autowired
    private InterestRepository interestRepository;

    @AfterEach
    public void cleanUp(){
        interestRepository.deleteAll();
        interestRepository.flush();
    }

    @Test
    public void saveShouldThrowExceptionWhenInterestNameEmpty() {
        InterestDto interest = createInterestDto();
        interest.setName(EMPTY_STRING);

        assertThrows(ConstraintViolationException.class, () -> interestService.save(interest));
    }

    @Test
    public void saveShouldSaveInterest() {
        InterestDto expected = createInterestDto();

        InterestDto actual = interestService.save(expected);

        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestNotExist() {
        InterestDto expected = InterestDto.builder()
                .message("Interest haven't been founded by id : " + ID).build();

        InterestDto actual = interestService.update(ID, expected);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void updateShouldThrowExceptionWhenInterestWithThisNameExist() {
        Interest interest = interestRepository.save(dtoToInterestConverter.convert(createInterestDto()));

        InterestDto expected = InterestDto.builder()
                .message("Interest have been founded with name " + interest.getName()).build();

        InterestDto actual = interestService.update(interest.getId(), interestToDtoConverter.convert(interest));

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void updateShouldUpdateInterest() {
        Interest interest = interestRepository.save(dtoToInterestConverter.convert(createInterestDto()));
        InterestDto expected = createInterestDto();
        expected.setName(ANOTHER_INTEREST_NAME);

        InterestDto actual = interestService.update(interest.getId(), expected);

        assertEquals(expected.getName(), actual.getName());
    }


    @Test
    public void deleteShouldThrowExceptionWhenInterestNotExist() {
        InterestDto expected = InterestDto.builder()
                .message("Interest haven't been founded by id : " + ID).build();

        InterestDto actual = interestService.delete(ID);

        assertEquals(expected.getMessage(), actual.getMessage());
    }

    @Test
    public void deleteShouldDeleteInterest() {
        Interest interest = interestRepository.save(dtoToInterestConverter.convert(createInterestDto()));

        interestService.delete(interest.getId());

        List<Interest> all = interestRepository.findAll();
        assertTrue(all.isEmpty());
    }

    @Test
    public void isExistShouldReturnFalseWhenInterestNotFound() {
        assertFalse(interestService.isExist(INTEREST_NAME));
    }

    @Test
    public void isExistShouldReturnInterest() {
        interestRepository.save(dtoToInterestConverter.convert(createInterestDto()));

        assertTrue(interestService.isExist(INTEREST_NAME));
    }

    @Test
    public void findByIdShouldReturnInterest() {
        InterestDto expected = interestToDtoConverter
                .convert(interestRepository.save(dtoToInterestConverter.convert(createInterestDto())));

        Optional<InterestDto> actual = interestService.findById(expected.getId());

        assertEquals(expected.getName(), actual.get().getName());
        assertEquals(expected.getId(), actual.get().getId());
    }
}
