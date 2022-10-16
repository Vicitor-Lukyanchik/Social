package com.social.repository;

import com.social.entity.Profile;
import com.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Profile findProfileByUser(User user);

    Optional<Profile> findByUser(User user);
}
