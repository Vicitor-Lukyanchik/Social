package com.social.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
@SequenceGenerator(
        name = "role-gen",
        sequenceName = "role_id_seq",
        initialValue = 1, allocationSize = 1)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role-gen")
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
