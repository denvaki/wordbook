package com.likorn_devaki.wordbook.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity(name = "UsersAndWords")
@Table(name = "words")
@NoArgsConstructor
@AllArgsConstructor
public class Words {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String foreignLan_word;

    @Column(nullable = false)
    private String translated_word;

    @Column(nullable = false)
    private Integer user_id;


}
