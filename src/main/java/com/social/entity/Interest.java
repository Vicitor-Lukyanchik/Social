package com.social.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Builder
@Table(name = "interest")
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 1, max = 50, message = "Interest name should be less than 50 and more than 1")
    private String name;

    public Interest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Interest(String name) {
        this.name = name;
    }
}
