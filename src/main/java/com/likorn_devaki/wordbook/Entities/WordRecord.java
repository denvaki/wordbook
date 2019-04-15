package com.likorn_devaki.wordbook.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
@Data
@Entity
@Table(name = "words")
@NoArgsConstructor
public class WordRecord {

    public WordRecord(Integer user_id, String foreign_word, String translated_word) {
        this.user_id = user_id;
        this.foreign_word = foreign_word;
        this.translated_word = translated_word;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer user_id;

    //TODO validate foreign_word
    @Column(nullable = false)
    private String foreign_word;

    //TODO validate translated_word
    @Column()
    private String translated_word;

    @Column(nullable = false)
    private String created;

    @Column
    private String deleted;

    public void setCreated(String created) {
        this.created = created;
    }
}
