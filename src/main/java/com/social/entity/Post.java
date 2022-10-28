package com.social.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @Column(name = "title")
    @Size(min = 1, max = 50, message = "Post title should be less than 50 and more than 1")
    private String title;

    @Column(name = "text")
    @Size(min = 1, max = 255, message = "Post text should be less than 255 and more than 1")
    private String text;

    @Column(name = "date_time")
    @NotNull(message = "Date and time can't be null")
    private LocalDateTime dateTime;

      public Post(Group group, Chat chat, String title, String text, LocalDateTime dateTime) {
        this.group = group;
        this.chat = chat;
        this.title = title;
        this.text = text;
        this.dateTime = dateTime;
    }
}
