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
@Builder
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Group name should be less than 50 and more than 1")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @ManyToMany(mappedBy = "joinGroups", fetch = FetchType.LAZY)
    private List<Profile> joinProfiles;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    private List<Post> posts;

    public Group(Long id, String name, Interest interest, List<Profile> joinProfiles, Profile profile, List<Post> posts) {
        this.id = id;
        this.name = name;
        this.interest = interest;
        this.joinProfiles = joinProfiles;
        this.profile = profile;
        this.posts = posts;
    }

    public Group(String name, Interest interest, List<Profile> joinProfiles, Profile profile, List<Post> posts) {
        this.name = name;
        this.interest = interest;
        this.joinProfiles = joinProfiles;
        this.profile = profile;
        this.posts = posts;
    }

    public Group(String name, Interest interest, Profile profile) {
        this.name = name;
        this.interest = interest;
        this.profile = profile;
    }
}
