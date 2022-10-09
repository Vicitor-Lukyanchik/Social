package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "groups")
@SequenceGenerator(name = "groups-gen", sequenceName = "groups_id_seq", initialValue = 1, allocationSize = 1)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "groups-gen")
    private Long id;

    @Column(name = "name")
    @Size(max = 50, message = "Group name should be less than 50")
    @NotBlank(message = "Group name can't be empty")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @ManyToMany(mappedBy = "joinGroups", fetch = FetchType.LAZY)
    private List<Profile> joinProfiles;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile createdProfile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<Post> posts;

    public Group(Long id, String name, Interest interest, List<Profile> joinProfiles, Profile createdProfile, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.interest = interest;
        this.joinProfiles = joinProfiles;
        this.createdProfile = createdProfile;
        this.posts = posts;
    }

    public Group(String name, Interest interest, List<Profile> joinProfiles, Profile createdProfile, List<Post> posts) {
        this.name = name;
        this.interest = interest;
        this.joinProfiles = joinProfiles;
        this.createdProfile = createdProfile;
        this.posts = posts;
    }

    public Group(){}

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

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public List<Profile> getJoinProfiles() {
        return joinProfiles;
    }

    public void setJoinProfiles(List<Profile> joinProfiles) {
        this.joinProfiles = joinProfiles;
    }

    public Profile getCreatedProfile() {
        return createdProfile;
    }

    public void setCreatedProfile(Profile createdProfile) {
        this.createdProfile = createdProfile;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Objects.equals(id, group.id) && Objects.equals(name, group.name) && Objects.equals(interest, group.interest) && Objects.equals(joinProfiles, group.joinProfiles) && Objects.equals(createdProfile, group.createdProfile) && Objects.equals(posts, group.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, interest, joinProfiles, createdProfile, posts);
    }
}
