package com.wevioo.cantine.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Lob
    @Column(name = "image")
    private byte[] image;

    private String password;

    @Column(name = "first_name")
    private String firstname;

    @Column(name = "last_name")
    private  String lastname;

    @Column(name = "resetToken")
    private String resetToken;

    @Column(name = "resetTokenExpiration")
    private LocalDateTime resetTokenExpiration;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    public User(String username, byte[] image, String password, String firstname, String lastname) {
        this.username = username;
        this.image = image;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User() {

    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
