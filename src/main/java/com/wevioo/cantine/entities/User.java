package com.wevioo.cantine.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

   // private String email;

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

    public User(String username/*, String email*/, String password, String firstname, String lastname) {
        this.username = username;
        //this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
    }

   /* public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }*/
}
