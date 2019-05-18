package com.likorn_devaki.wordbook.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;
import java.util.List;

@Data
@Entity
@Table(name = "tags")
@NoArgsConstructor
public class Tag {

    public Tag(User user, String name) {
        this.user = user;
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
    private Color color;

    @ManyToOne(optional = false)
    private User user;

    @ManyToMany()
    private List<Word> words;
}
