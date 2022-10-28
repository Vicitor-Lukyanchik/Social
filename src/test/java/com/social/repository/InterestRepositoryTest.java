package com.social.repository;

import com.social.entity.Interest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.social.Constants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void test() {
        assertThat(interestRepository).isNotNull();
    }

    @Test
    public void saveShouldSaveInterest() {
        Interest expected = new Interest(INTEREST_NAME);
        Interest actual = interestRepository.save(expected);
        assertEquals(expected.getName(), actual.getName());
    }
}
