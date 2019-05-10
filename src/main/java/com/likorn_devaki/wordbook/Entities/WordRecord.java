package com.likorn_devaki.wordbook.Entities;

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
        this.userId = user_id;
        this.foreignWord = foreign_word;
        this.translatedWord = translated_word;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    //TODO validate foreign_word
    @Column(nullable = false)
    private String foreignWord;

    //TODO validate translated_word
    @Column()
    private String translatedWord;

    @Column(nullable = false)
    private String created;

    @Column()
    private String tag;

    public void setCreated(String created) {
        this.created = created;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setForeign_word(String foreign_word) {
        this.foreignWord = foreign_word;
    }

    public void setTranslated_word(String translated_word) {
        this.translatedWord = translated_word;
    }

    public Integer getId() {
        return id;
    }
}
