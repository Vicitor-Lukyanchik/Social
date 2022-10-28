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
@AllArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @ManyToMany(mappedBy = "joinGroups", fetch = FetchType.EAGER)
    private List<Profile> joinProfiles;

    @ManyToOne(fetch =  FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "group")
    private List<Post> posts;
}
