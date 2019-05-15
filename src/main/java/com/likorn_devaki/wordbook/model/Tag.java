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
class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer userId;

    @Column()
    private String description;

    @Column()
    private Color color;

    @ManyToMany()
    private List<Word> words;
}
