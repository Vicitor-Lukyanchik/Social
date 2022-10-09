package com.social.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "interest")
@SequenceGenerator(
        name = "interest-gen",
        sequenceName = "interest_id_seq",
        initialValue = 1, allocationSize = 1)
public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interest-gen")
    private Long id;

    @Column(name = "name")
    @Size(max = 50, message = "Interest name should be less than 50")
    @NotBlank(message = "Interest name can't be empty")
    private String name;

    public Interest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Interest(String name) {
        this.name = name;
    }

    public Interest() {
    }

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
}
