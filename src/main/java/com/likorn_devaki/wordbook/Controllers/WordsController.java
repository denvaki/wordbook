package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.WordRecord;
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
    public WordRecord saveWord(@RequestBody WordRecord wordRecord){
        wordRecord.setCreated(LocalDateTime.now().toString());
        return wordsRepo.save(wordRecord);
    }

    @GetMapping(path = "all_words")
    public List<WordRecord> getAllWords() {
        System.out.println(new ArrayList<>(wordsRepo.findAll()));
        return new ArrayList<>(wordsRepo.findAll());
    }

    @GetMapping(path = "all_words_where_user_id/{user_id}")
    public List<WordRecord> getAllWordsByUserId(@PathVariable String user_id) {
        return wordsRepo.findAllByUserId(Integer.parseInt(user_id));
    }

    @PutMapping(path = "update_word/{word_id}")
    public WordRecord updateWord(@PathVariable Integer word_id, @RequestBody WordRecord wordRecord) {
        wordRecord.setId(word_id);
        return wordsRepo.save(wordRecord);
    }

    @DeleteMapping(path = "delete_word/{word_id}")
    public void deleteWord(@PathVariable Integer word_id) {
        wordsRepo.deleteById(word_id);
    }
}