package com.social.service;

import com.social.entity.Chat;
import com.social.service.exception.ServiceException;

public interface ChatService {

    Chat save(Chat chat);

    boolean isExist(Chat chat);

    Chat findById(Long id) throws ServiceException;

}
