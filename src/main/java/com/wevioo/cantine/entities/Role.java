package com.wevioo.cantine.entities;

import com.wevioo.cantine.enums.enumRole;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private enumRole name;

    public Role() {

    }

    public Role(enumRole name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public enumRole getName() {
        return name;
    }

    public void setName(enumRole name) {
        this.name = name;
    }
}
