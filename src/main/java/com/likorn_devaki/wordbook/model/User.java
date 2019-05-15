package com.likorn_devaki.wordbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(nullable = false)
    private LocalDateTime created;

    @OneToMany(mappedBy = "user")
    private List<Tag> tags;

}
