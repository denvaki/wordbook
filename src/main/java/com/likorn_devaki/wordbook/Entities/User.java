package com.likorn_devaki.wordbook.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings({"unused", "FieldCanBeLocal"})
@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class User {

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    //TODO encrypt password
    @Column(nullable = false)
    private String password;

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(nullable = false)
    private String created;

    public void setCreated(String created) {
        this.created = created;
    }
}
