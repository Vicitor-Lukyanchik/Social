package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "message")
@SequenceGenerator(
        name = "message-gen",
        sequenceName = "message_id_seq",
        initialValue = 1, allocationSize = 1)
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message-gen")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "text")
    @Size(max = 255, message = "Message should be less than 255")
    @NotBlank(message = "Message text can't be empty")
    private String text;

    @Column(name = "date_time")
    @NotNull(message = "Date and time can't be null")
    private LocalDateTime dateTime;

    public Message(Long id, Chat chat, Profile profile, String text, LocalDateTime dateTime) {
        this.id = id;
        this.chat = chat;
        this.profile = profile;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(Chat chat, Profile profile, String text, LocalDateTime dateTime) {
        this.chat = chat;
        this.profile = profile;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(Profile profile, String text, LocalDateTime dateTime) {
        this.profile = profile;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(String text, LocalDateTime dateTime) {
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(chat, message.chat) && Objects.equals(profile, message.profile) && Objects.equals(text, message.text) && Objects.equals(dateTime, message.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, chat, profile, text, dateTime);
    }
}
