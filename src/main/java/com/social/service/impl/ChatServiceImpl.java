package com.social.service.impl;

import com.social.entity.Chat;
import com.social.repository.ChatRepository;
import com.social.service.ChatService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public Chat save(@Valid Chat chat) {
        Chat result = chatRepository.save(chat);
        return result;
    }

    @Override
    @Transactional
    public boolean isExist(Chat chat) {
        try {
            findById(chat.getId());
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Chat findById(Long id) {
        Optional<Chat> chat = chatRepository.findById(id);

        if (chat.isEmpty()) {
            throw new ServiceException("Chat haven't been founded by id : " + id);
        }
        return chat.get();
    }
}
