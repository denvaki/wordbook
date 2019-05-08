package com.likorn_devaki.wordbook.Entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
    @Setter
    @Column(nullable = false)
    private LocalDateTime created;


}
