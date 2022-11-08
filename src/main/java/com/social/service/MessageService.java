package com.social.service;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;
import com.social.exception.ServiceException;

import java.util.List;

public interface MessageService {

    Message save(Message message);

    void sendMessage(Profile profile, Message message, Chat chat) throws ServiceException;

    List<Message> findAllByChat(Chat chat) throws ServiceException;
}
