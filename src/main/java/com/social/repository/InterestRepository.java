package com.social.repository;

import com.social.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InterestRepository extends JpaRepository<Interest, Long> {

    Optional<Interest> findByName(String name);

    void deleteById(Long id);
}
