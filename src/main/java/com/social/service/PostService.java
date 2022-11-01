package com.social.service;

import com.social.entity.*;
import com.social.service.exception.ServiceException;

import javax.validation.Valid;
import java.util.List;

public interface PostService {

    Post save(Post post, Long groupId) throws ServiceException;

    List<Post> findAllByGroupId(Long groupId) throws ServiceException;

    void sendPostMessage(Profile profile, Post post, Message message) throws ServiceException;

    List<Message> findMessagesByPost(Post post) throws ServiceException;

    boolean isExist(Post post);

    Post findById(Long id) throws ServiceException;
}
