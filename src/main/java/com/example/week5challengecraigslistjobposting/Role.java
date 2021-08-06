package com.example.week5challengecraigslistjobposting;

import javax.persistence.*;

@Entity
@Table(name = "role_table")
public class Role {

    //DB generating id automatically
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "role")
    private String role;

    //constructors
    public Role(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public Role() {

    }


    //getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
