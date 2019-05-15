package com.likorn_devaki.wordbook.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.awt.*;

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

    @Column()
    private String description;

    @Column()
    private Color color;
}
