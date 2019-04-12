package com.likorn_devaki.wordbook.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "words")
@NoArgsConstructor
@AllArgsConstructor

public class WordRecord {

    public WordRecord(Integer user_id, String foreign_word, String translated_word) {
        this.user_id = user_id;
        this.foreign_word = foreign_word;
        this.translated_word = translated_word;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column()
    private Integer user_id;

    @Column(table = "words", nullable = false)
    private String foreign_word;

    @Column(table = "words", nullable = false)
    private String translated_word;
}
