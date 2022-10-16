package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chat")
@SequenceGenerator(
        name = "chat-gen",
        sequenceName = "chat_id_seq",
        initialValue = 1, allocationSize = 1)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat-gen")
    private Long id;

    @Column(name = "name")
    @Size(max = 50, message = "Chat name should be less than 50")
    @NotBlank(message = "Chat name can't be empty")
    private String name;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.LAZY)
    private List<Profile> profiles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private List<Message> messages;

    public Chat(Long id, String name, List<Profile> profiles, List<Message> messages) {
        this.id = id;
        this.name = name;
        this.profiles = profiles;
        this.messages = messages;
    }

    public Chat(Long id, String name, List<Profile> profiles) {
        this.id = id;
        this.name = name;
        this.profiles = profiles;
    }

    public Chat(String name, List<Profile> profiles, List<Message> messages) {
        this.name = name;
        this.profiles = profiles;
        this.messages = messages;
    }

    public Chat(String name, List<Profile> profiles) {
        this.name = name;
        this.profiles = profiles;
    }

    public Chat(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(name, chat.name) && Objects.equals(profiles, chat.profiles) && Objects.equals(messages, chat.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, profiles, messages);
    }
}
