package com.social.service;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;

import javax.validation.Valid;
import java.util.List;

public interface MessageService {

    Message save(@Valid Message message);

    void sendMessage(Profile profile, @Valid Message message, Chat chat);

    List<Message> findAllByChat(Chat chat);
}
