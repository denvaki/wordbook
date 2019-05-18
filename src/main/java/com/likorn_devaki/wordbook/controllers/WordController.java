package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordController {

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

    @GetMapping(path = "words")
    public ResponseEntity findAll(
            @RequestParam(value = "foreign_word", required = false) String foreignWord,
            @RequestParam(value = "translated_word", required = false) String translatedWord,
            @RequestParam(value = "tag", required = false) String tag,
            HttpServletRequest req) {
        JWTProvider jwtProvider = new JWTProvider();
        String token = jwtProvider.resolveToken(req).toString();
        if (jwtProvider.invalidToken(token)){
            return ResponseEntity.badRequest().body("Bad credentials");
        }
        List<Word> usersWords = wordsService.findAllByUsername(foreignWord, translatedWord, tag, jwtProvider.getUsername(token));
        return ResponseEntity.ok(usersWords);
    }

    @PutMapping(path = "update_word")
    public Word update(@RequestParam(value = "word_id") Integer wordId, @RequestBody Word word) {
        // TODO refactor the method to remove the wordId parameter
        return wordsService.update(wordId, word);
    }

    @DeleteMapping(path = "delete_word")
    public void delete(@RequestParam(value = "word_id") Integer wordId) {
        wordsService.delete(wordId);
    }
}