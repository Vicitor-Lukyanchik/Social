package com.social.repository;

import com.social.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User entity);

    User findByUsername(String username);
}
