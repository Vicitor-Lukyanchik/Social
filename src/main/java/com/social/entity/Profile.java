package com.social.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname")
    @Size(min = 2, max = 50, message = "Firstname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in firstname should be uppercase")
    private String firstname;

    @Column(name = "lastname")
    @Size(min = 2, max = 50, message = "Lastname should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in lastname should be uppercase")
    private String lastname;

    @Column(name = "email")
    @Size(max = 100, message = "Email should be less than 100")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name = "age")
    @Min(value = 6, message = "Age should be more than 6")
    @Max(value = 120, message = "Age should less than 120")
    private Integer age;

    @Column(name = "town")
    @Size(min = 2, max = 50, message = "Town name should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,99}$", message = "First letter in town should be uppercase")
    private String town;

    @Column(name = "phone")
    @Size(min = 7, max = 15, message = "Phone should be more than 7 and less than 15")
    private String phone;

    @Column(name = "family_status")
    @Size(max = 50, message = "Family status should be less than 50")
    private String familyStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_group",
            joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")})
    private List<Group> joinGroups;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "profile")
    private List<Group> createdGroups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_chat",
            joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")})
    private List<Chat> chats;

    public Profile(String firstname, String lastname, String email, Sex sex, Integer age, String town, String phone,
                   String familyStatus, User user, List<Group> joinGroups, List<Group> createdGroups, List<Chat> chats) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.town = town;
        this.phone = phone;
        this.familyStatus = familyStatus;
        this.user = user;
        this.joinGroups = joinGroups;
        this.createdGroups = createdGroups;
        this.chats = chats;
    }

    public Profile(String firstname, String lastname, String email, Sex sex, Integer age, String town,
                   String phone, String familyStatus, User user) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.town = town;
        this.phone = phone;
        this.familyStatus = familyStatus;
        this.user = user;
    }

    public Profile(String firstname, String lastname, String email, Sex sex, Integer age, User user) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.user = user;
    }

    public Profile(String firstname, String lastname, String email, Sex sex, Integer age, String town, String phone, String familyStatus) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.sex = sex;
        this.age = age;
        this.town = town;
        this.phone = phone;
        this.familyStatus = familyStatus;
    }

    public Profile(Long id, String firstname, String lastname, String email, Integer age) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
    }

    public Profile(String firstname, String lastname, String email, Integer age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
    }
}
