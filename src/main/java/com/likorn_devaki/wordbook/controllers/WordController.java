package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.likorn_devaki.wordbook.JWT.JWTProvider.invalidToken;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordController {

    @Autowired
    private WordsService wordsService;
    private ResponseEntity<UserResponse> responseReLogIn =
            ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());

    @PostMapping(path = "save_word")
    public ResponseEntity<UserResponse> save(@RequestBody Word word, HttpServletRequest request) {
        return wordsService.save(word, request);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findOne(@PathVariable Integer id, HttpServletRequest request) {
        return wordsService.findOne(id, request);
    }

    @GetMapping(path = "words")
    public ResponseEntity<UserResponse> findAll(
            @RequestParam(value = "foreign_word", required = false) String foreignWord,
            @RequestParam(value = "translated_word", required = false) String translatedWord,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest request) {
        return wordsService.findAllByUsername(foreignWord, translatedWord, tag, request);
    }

    @PutMapping(path = "update_word")
    public ResponseEntity<UserResponse> update(@RequestBody Word word, HttpServletRequest request) {
        return wordsService.update(word, request);
    }

    @PutMapping(path = "add_tag")
    public ResponseEntity<UserResponse> addTag(
            @RequestBody Word word,
            @RequestParam(value = "tag_id") Integer tagId,
            HttpServletRequest request) {
        if (invalidToken(request))
            return responseReLogIn;
        return wordsService.addTag(word, tagId);
    }

    @DeleteMapping(path = "delete_word")
    public ResponseEntity<UserResponse> delete(@RequestParam(value = "word_id") Integer wordId, HttpServletRequest request) {
        return wordsService.delete(wordId, request);
    }
}