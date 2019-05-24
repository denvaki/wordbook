package com.likorn_devaki.wordbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

@Data
@Entity
@Table(name = "words")
@NoArgsConstructor
public class Word {

    public Word(Integer userId, String foreignWord, String translatedWord) {
        this.userId = userId;
        this.foreignWord = foreignWord;
        this.translatedWord = translatedWord;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    //TODO validate foreign_word
    @Column(nullable = false)
    private String foreignWord;

    @Column(nullable = false)
    private String translatedWord;

    @Column(nullable = false)
    private String created;

    @ElementCollection
    private Set<String> tagIds = Collections.emptySet();

    public void addTag(String tagId) {
        tagIds.add(tagId);
    }
}
