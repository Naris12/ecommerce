package com.shopme.common.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="roles")
@Data

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 40,nullable = false,unique = true)
    private String name;
    @Column(length = 100,nullable = false)
    private String description;

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Role(String name) {
        this.name = name;

    }
    public Role() {

    }
}
