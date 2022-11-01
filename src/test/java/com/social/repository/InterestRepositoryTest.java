package com.social.repository;

import com.social.entity.Interest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.social.Constants.INTEREST_NAME;
import static com.social.util.MockUtils.createInterest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;
    @AfterEach
    public void cleanUp(){
        interestRepository.deleteAll();
        interestRepository.flush();

    }

    @Test
    public void saveShouldSaveInterest() {
        Interest expected = createInterest();
        Interest actual = interestRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
    }
}
