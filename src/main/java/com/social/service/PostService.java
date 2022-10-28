package com.social.service;

import com.social.entity.*;

import javax.validation.Valid;
import java.util.List;

public interface PostService {

    Post save(@Valid Post post, Group group);

    List<Post> findAllByGroup(Group group);

    void sendPostMessage(Profile profile, Post post, @Valid Message message);

    List<Message> findMessagesByPost(Post post);

    boolean isExist(Post post);

    Post findById(Long id);
}
