package com.social.service;

import com.social.entity.Chat;

import javax.validation.Valid;

public interface ChatService {

    Chat save(@Valid Chat chat);

    boolean isExist(Chat chat);

    Chat findById(Long id);

}
