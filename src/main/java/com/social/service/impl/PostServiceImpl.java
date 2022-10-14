package com.social.service.impl;

import com.social.entity.*;
import com.social.repository.PostRepository;
import com.social.service.*;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final GroupService groupService;
    private final ChatService chatService;
    private final MessageService messageService;

    @Override
    @Transactional
    public Post save(@Valid Post post, Group group) {
        isGroupExist(group);
        return buildPost(post, group);
    }

    private Post buildPost(Post post, Group group) {
        post.setGroup(group);
        Chat chat = chatService.save(new Chat(post.getTitle(), group.getJoinProfiles()));
        post.setChat(chat);
        Post result = postRepository.save(post);
        return result;
    }

    @Override
    @Transactional
    public List<Post> findAllByGroup(Group group) {
        isGroupExist(group);
        List<Post> posts = postRepository.findAllByGroup(group);
        return posts.stream().sorted(Comparator.comparing(Post::getDateTime)).collect(Collectors.toList());
    }

    private void isGroupExist(Group group) {
        if (!groupService.isExist(group)) {
            throw new ServiceException("Group haven't been founded by id " + group.getId());
        }
    }

    @Override
    @Transactional
    public void sendPostMessage(Profile profile, Post post, @Valid Message message) {
        if (!isExist(post)) {
            throw new ServiceException("Post haven't been founded by id : " + post.getId());
        }
        isInGroup(profile, post.getGroup());

        messageService.sendMessage(profile, message, post.getChat());
    }

    @Override
    @Transactional
    public List<Message> findMessagesByPost(Post post) {
        if (!isExist(post)) {
            throw new ServiceException("Post haven't been founded by id : " + post.getId());
        }

        return messageService.findAllByChat(post.getChat());
    }

    private void isInGroup(Profile profile, Group group) {
        Optional<Profile> result = group.getJoinProfiles().stream()
                .filter(p -> profile.getId().equals(p.getId())).findFirst();

        if (result.isEmpty()) {
            throw new ServiceException("Profile by id: " + profile.getId() +
                    " haven't been founded in group by id : " + group.getId());
        }
    }

    @Override
    @Transactional
    public boolean isExist(Post post) {
        try {
            findById(post.getId());
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Post findById(Long id) {
        Optional<Post> post = postRepository.findById(id);

        if (post.isEmpty()) {
            throw new ServiceException("Post haven't been founded by id : " + id);
        }
        return post.get();
    }
}
