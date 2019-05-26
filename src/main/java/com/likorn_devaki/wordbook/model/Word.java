package com.likorn_devaki.wordbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
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

    @Column(nullable = false)
    private String foreignWord;

    @Column(nullable = false)
    private String translatedWord;

    @Column(nullable = false)
    private String created;

    @ElementCollection
    private Set<Integer> tagIds;

    public void addTag(Integer tagId) {
        if (tagIds == null)
            tagIds = new HashSet<>();
        tagIds.add(tagId);
    }
}
