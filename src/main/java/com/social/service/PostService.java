package com.social.service;

import com.social.entity.*;

import java.util.List;

public interface PostService {

    Post save(Post post, Group group);

    List<Post> findAllByGroup(Group group);

    void sendPostMessage(Profile profile, Post post, Message message);

    List<Message> findMessagesByPost(Post post);

    boolean isExist(Post post);

    Post findById(Long id);
}
