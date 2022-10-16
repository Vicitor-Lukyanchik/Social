package com.social.repository;

import com.social.entity.Interest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    public void test() throws Exception {
        assertThat(interestRepository).isNotNull();
    }

    @Test
    public void save_ShouldSaveUser() throws Exception {
        Interest expected = new Interest("football");
        Interest actual = interestRepository.save(expected);
        expected.setId(1L);
        assertEquals(expected, actual);
    }
}
