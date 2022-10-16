package com.social.repository;

import com.social.entity.Chat;
import com.social.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllByChat(Chat chat);
}
