package com.likorn_devaki.wordbook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "tagIds")
@NoArgsConstructor
public class Tag {

    public Tag(Integer userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column()
    private String description;

    @Column()
    private String color;

    @Column(nullable = false)
    private Integer userId;

    @JsonIgnore
    @ManyToMany()
    private List<Word> words;

    public void addWord(Word word) {
        words.add(word);
    }
}
