package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.dto.ResponseToUsr;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordController {

    @Autowired
    private WordsService wordsService;

    @PostMapping(path = "save_word")
    public ResponseEntity<ResponseToUsr> save(@RequestBody Word word, HttpServletRequest request) {
        return wordsService.save(word, request);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseToUsr> findOne(@PathVariable Integer id, HttpServletRequest request) {
        return wordsService.findOne(id, request);
    }

    @GetMapping(path = "words")
    public ResponseEntity<ResponseToUsr> findAll(
            @RequestParam(value = "foreign_word", required = false) String foreignWord,
            @RequestParam(value = "translated_word", required = false) String translatedWord,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request) {
        return wordsService.findAllByUsername(foreignWord, translatedWord, tag, request);
    }

    @PutMapping(path = "update_word")
    public ResponseEntity<ResponseToUsr> update(@RequestBody Word word, HttpServletRequest request) {
        return wordsService.update(word, request);
    }

    @PutMapping(path = "add_tag")
    public ResponseEntity<ResponseToUsr> addTag(@RequestBody Word word, @RequestBody Tag tag, HttpServletRequest request) {
        return wordsService.addTag(word, tag, request);
    }

    @DeleteMapping(path = "delete_word")
    public ResponseEntity<ResponseToUsr> delete(@RequestParam(value = "word_id") Integer wordId, HttpServletRequest request) {
        return wordsService.delete(wordId, request);
    }
}