package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findProfileByUserId(Long userId);

    Optional<Profile> findByUser(User user);
}
