package com.social.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Chat name should be less than 50 and more than 1")
    private String name;

    @ManyToMany(mappedBy = "chats", fetch = FetchType.EAGER)
    private List<Profile> profiles;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chat")
    private List<Message> messages;
}
