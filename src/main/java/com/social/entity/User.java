package com.social.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    @Size(min = 4, max = 50, message = "Username should be more then 4 and less than 50")
    private String username;

    @Column(name = "password")
    @Size(min = 4, max = 30, message = "Password should be more then 4 and less than 30")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public User(Long id, String username, String password, List<Role> roles, Status status) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public User(String username, String password, List<Role> roles, Status status) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.status = status;
    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, Status status) {
        this.username = username;
        this.password = password;
        this.status = status;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
