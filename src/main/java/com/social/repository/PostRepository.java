package com.social.repository;

import com.social.entity.Group;
import com.social.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByGroup(Group group);
}
