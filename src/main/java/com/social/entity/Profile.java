package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "profile")
@SequenceGenerator(
        name = "profile-gen",
        sequenceName = "profile_id_seq",
        initialValue = 1, allocationSize = 1)
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile-gen")
    private Long id;

    @Column(name = "firstname")
    @Size(min = 2, max = 50, message = "Firstname should be more than 2 and less than 50")
    @NotBlank(message = "Firstname can't be empty")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in firstname should be uppercase")
    private String firstname;

    @Column(name="lastname")
    @Size(min = 2, max = 50, message = "Lastname should be more than 2 and less than 50")
    @NotBlank(message = "Lastname can't be empty")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in lastname should be uppercase")
    private String lastname;

    @Column(name= "email")
    @NotBlank(message = "Email can't be empty")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email should be less than 100")
    private String email;

    @Column(name = "sex")
    @Size(max=6, message = "Sex should be more than 6")
    @Pattern(regexp = "FEMALE|MALE|-")
    private String sex;

    @Column(name = "age")
    @Min(value = 6, message = "Age should be more than 6")
    @Max(value = 120, message = "Age should less than 120")
    private Integer age;

    @Column(name = "town")
    @Size(min = 2, max = 50, message = "Town name should be more than 2 and less than 50")
    @Pattern(regexp = "^[A-Z][a-z0-9_-]{3,19}$", message = "First letter in town should be uppercase")
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdProfile")
    private List<Group> createdGroups;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "profile_chat",
            joinColumns = {@JoinColumn(name = "profile_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "chat_id", referencedColumnName = "id")})
    private List<Chat> chats;

    public Profile(Long id, String firstname, String lastname, String email, String sex, Integer age, String town,
                   String phone, String familyStatus, User user, List<Group> joinGroups, List<Group> createdGroups, List<Chat> chats) {
        this.id = id;
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

    public Profile(String firstname, String lastname, String email, String sex, Integer age, String town, String phone,
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

    public Profile(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFamilyStatus() {
        return familyStatus;
    }

    public void setFamilyStatus(String familyStatus) {
        this.familyStatus = familyStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Group> getJoinGroups() {
        return joinGroups;
    }

    public void setJoinGroups(List<Group> joinGroups) {
        this.joinGroups = joinGroups;
    }

    public List<Group> getCreatedGroups() {
        return createdGroups;
    }

    public void setCreatedGroups(List<Group> createdGroups) {
        this.createdGroups = createdGroups;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(id, profile.id) && Objects.equals(firstname, profile.firstname) &&
                Objects.equals(lastname, profile.lastname) && Objects.equals(email, profile.email) &&
                Objects.equals(sex, profile.sex) && Objects.equals(age, profile.age) &&
                Objects.equals(town, profile.town) && Objects.equals(phone, profile.phone) &&
                Objects.equals(familyStatus, profile.familyStatus) && Objects.equals(user, profile.user) &&
                Objects.equals(joinGroups, profile.joinGroups) &&
                Objects.equals(createdGroups, profile.createdGroups) && Objects.equals(chats, profile.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, email, sex, age, town, phone, familyStatus, user,
                joinGroups, createdGroups, chats);
    }
}
