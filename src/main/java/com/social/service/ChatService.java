package com.social.service;

import com.social.entity.Chat;

public interface ChatService {

    Chat save(Chat chat);

    boolean isExist(Chat chat);

    Chat findById(Long id);

}
