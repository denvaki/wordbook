package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.entities.Word;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordsController {

    @Autowired
    private WordsRepo wordsRepo;

    //@Transactional
    @PostMapping(path = "save_word")
    public Word saveWord(@RequestBody Word word){
        word.setCreated(LocalDateTime.now().toString());
        return wordsRepo.save(word);
    }

    @GetMapping(path = "all_words")
    public List<Word> getAllWords() {
        System.out.println(new ArrayList<>(wordsRepo.findAll()));
        return new ArrayList<>(wordsRepo.findAll());
    }

    @GetMapping(path = "all_words_where_user_id/{user_id}")
    public List<Word> getAllWordsByUserId(@PathVariable String user_id) {
        return wordsRepo.findAllByUserId(Integer.parseInt(user_id));
    }

    @PutMapping(path = "update_word/{word_id}")
    public Word updateWord(@PathVariable Integer word_id, @RequestBody Word word) {
        word.setId(word_id);
        return wordsRepo.save(word);
    }

    @DeleteMapping(path = "delete_word/{word_id}")
    public void deleteWord(@PathVariable Integer word_id) {
        wordsRepo.deleteById(word_id);
    }
}