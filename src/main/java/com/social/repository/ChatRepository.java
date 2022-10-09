package com.social.repository;

import com.social.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findById(Long id);
}
