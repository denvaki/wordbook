package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordsController {

    @Autowired
    private WordsService wordsService;

    @PostMapping(path = "save_word")
    public Word save(@RequestBody Word word){
        return wordsService.save(word);
    }

    @GetMapping("{id}")
    public Word findOne(@PathVariable Integer id) {
        return wordsService.findOne(id);
    }

    @GetMapping(path = "all_words")
    public List<Word> findAll(
            @RequestParam(value = "foreign_word", required = false) String foreignWord,
            @RequestParam(value = "translated_word", required = false) String translatedWord,
            @RequestParam(value = "tag", required = false) String tag) {
        return wordsService.findAll(foreignWord, translatedWord, tag);
    }

    @PutMapping(path = "update_word/{word_id}")
    public Word update(@PathVariable Integer word_id, @RequestBody Word word) {
        return wordsService.update(word_id, word);
    }

    @DeleteMapping(path = "delete_word/{word_id}")
    public void delete(@PathVariable Integer word_id) {
        wordsService.delete(word_id);
    }
}