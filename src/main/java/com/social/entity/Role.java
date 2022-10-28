package com.social.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public Role(String name, List<User> users, Status status) {
        this.name = name;
        this.users = users;
        this.status = status;
    }

    public Role(String name, Status status) {
        this.name = name;
        this.status = status;
    }

    public Role(String name) {
        this.name = name;
    }
}
