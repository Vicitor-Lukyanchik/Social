package com.social.service.impl;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;
import com.social.repository.MessageRepository;
import com.social.service.ChatService;
import com.social.service.MessageService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatService chatService;

    @Override
    @Transactional
    public Message save(@Valid Message message) {
        Message result = messageRepository.save(message);
        return result;
    }

    @Override
    @Transactional
    public void sendMessage(Profile profile, @Valid Message message, Chat chat) {
        if (!chatService.isExist(chat)) {
            throw new ServiceException("Chat haven't been founded by id " + chat.getId());
        }
        message.setChat(chat);
        message.setProfile(profile);
        save(message);
    }

    @Override
    @Transactional
    public List<Message> findAllByChat(@Valid Chat chat) {
        if (!chatService.isExist(chat)) {
            throw new ServiceException("Chat haven't been founded by id " + chat.getId());
        }

        List<Message> chats = messageRepository.findAllByChat(chat);
        return chats.stream().sorted(Comparator.comparing(Message::getDateTime)).collect(Collectors.toList());
    }
}