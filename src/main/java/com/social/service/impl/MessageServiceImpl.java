package com.social.service.impl;

import com.social.entity.Chat;
import com.social.entity.Message;
import com.social.entity.Profile;
import com.social.repository.MessageRepository;
import com.social.service.ChatService;
import com.social.service.MessageService;
import com.social.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    public Message save(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void sendMessage(Profile profile, Message message, Chat chat) throws ServiceException {
        if (!chatService.isExist(chat)) {
            throw new ServiceException("Chat haven't been founded by id " + chat.getId());
        }
        message.setChat(chat);
        message.setProfile(profile);
        save(message);
    }

    @Override
    public List<Message> findAllByChat(Chat chat) throws ServiceException {
        if (!chatService.isExist(chat)) {
            throw new ServiceException("Chat haven't been founded by id " + chat.getId());
        }

        List<Message> chats = messageRepository.findAllByChat(chat);
        return chats.stream().sorted(Comparator.comparing(Message::getDateTime)).collect(Collectors.toList());
    }
}
