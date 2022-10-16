package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
@SequenceGenerator(
        name = "post-gen",
        sequenceName = "post_id_seq",
        initialValue = 1, allocationSize = 1)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post-gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "title")
    @Size(max = 80, message = "Post title should be less than 80")
    @NotBlank(message = "Post title can't be empty")
    private String title;

    @Column(name = "text")
    @Size(max = 255, message = "Post text should be less than 255")
    @NotBlank(message = "Post text can't be empty")
    private String text;

    @Column(name = "date_time")
    @NotNull(message = "Date and time can't be null")
    private LocalDateTime dateTime;

    public Post(Long id, Group group, Chat chat, String title, String text, LocalDateTime dateTime) {
        this.id = id;
        this.group = group;
        this.chat = chat;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Post(Group group, Chat chat, String title, String text, LocalDateTime dateTime) {
        this.group = group;
        this.chat = chat;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Post(String title, String text, LocalDateTime dateTime) {
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Post() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(group, post.group) && Objects.equals(chat, post.chat) && Objects.equals(title, post.title) && Objects.equals(text, post.text) && Objects.equals(dateTime, post.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, group, chat, title, text, dateTime);
    }
}
