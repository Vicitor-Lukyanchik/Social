package com.social.service;

import com.social.entity.*;

import javax.validation.Valid;
import java.util.List;

public interface PostService {

    Post save(@Valid Post post, Long groupId);

    List<Post> findAllByGroupId(Long groupId);

    void sendPostMessage(Profile profile, Post post, @Valid Message message);

    List<Message> findMessagesByPost(Post post);

    boolean isExist(Post post);

    Post findById(Long id);
}
