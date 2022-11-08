package com.social.service.impl;

import com.social.entity.*;
import com.social.repository.PostRepository;
import com.social.service.ChatService;
import com.social.service.GroupService;
import com.social.service.MessageService;
import com.social.service.PostService;
import com.social.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    public Post save(Post post, Long groupId) throws ServiceException {
        isGroupExist(groupId);
        return buildPost(post, groupService.findById(groupId));
    }

    private Post buildPost(Post post, Group group) {
        post.setGroup(group);
        Chat chat = chatService.save(Chat.builder().name(post.getTitle())
                .profiles(group.getJoinProfiles()).build());
        post.setChat(chat);
        Post result = postRepository.save(post);
        return result;
    }

    @Override
    public List<Post> findAllByGroupId(Long groupId) throws ServiceException {
        isGroupExist(groupId);
        List<Post> posts = postRepository.findAllByGroup(groupService.findById(groupId));
        return posts.stream().sorted(Comparator.comparing(Post::getDateTime)).collect(Collectors.toList());
    }

    private void isGroupExist(Long id) throws ServiceException {
        if (!groupService.isExist(id)) {
            throw new ServiceException("Group haven't been founded by id " + id);
        }
    }

    @Override
    public void sendPostMessage(Profile profile, Post post, Message message) throws ServiceException {
        if (!isExist(post)) {
            throw new ServiceException("Post haven't been founded by id : " + post.getId());
        }
        isInGroup(profile, post.getGroup());

        messageService.sendMessage(profile, message, post.getChat());
    }

    @Override
    public List<Message> findMessagesByPost(Post post) throws ServiceException {
        if (!isExist(post)) {
            throw new ServiceException("Post haven't been founded by id : " + post.getId());
        }

        return messageService.findAllByChat(post.getChat());
    }

    private void isInGroup(Profile profile, Group group) throws ServiceException {
        Optional<Profile> result = group.getJoinProfiles().stream()
                .filter(p -> profile.getId().equals(p.getId())).findFirst();

        if (result.isEmpty()) {
            throw new ServiceException("Profile by id: " + profile.getId() +
                    " haven't been founded in group by id : " + group.getId());
        }
    }

    @Override
    public boolean isExist(Post post) {
        try {
            findById(post.getId());
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    @Override
    public Post findById(Long id) throws ServiceException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Post haven't been founded by id : " + id));
        return post;
    }
}
