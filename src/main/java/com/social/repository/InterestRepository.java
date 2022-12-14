package com.social.repository;

import com.social.entity.Interest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InterestRepository extends JpaRepository<Interest, Long>, JpaSpecificationExecutor<Interest> {

    Optional<Interest> findByName(String name);

    void deleteById(Long id);
}
