package com.social.repository;

import com.social.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface InterestRepository extends JpaRepository<Interest, Long> {

    Interest findByName(String name);
}
