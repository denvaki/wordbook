package com.likorn_devaki.wordbook.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "user_entity")
@NoArgsConstructor
@AllArgsConstructor
@SecondaryTable(name = "words", pkJoinColumns = @PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id"))
public class Users {

    public Users(String username, String password, String foreignLan_word, String translated_word) {
        this.username = username;
        this.password = password;
        this.foreignLan_word = foreignLan_word;
        this.translated_word = translated_word;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(table = "words", nullable = false)
    private String foreignLan_word;

    @Column(table = "words", nullable = false)
    private String translated_word;
}
