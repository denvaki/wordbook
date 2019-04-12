package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entity.WordsRepo;
import com.likorn_devaki.wordbook.Entity.WordRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WordBookController {

    @Autowired
    private WordsRepo wordsRepo;

    @PostMapping(path = "save_word")
    public @ResponseBody
    void save_word(@RequestBody WordRecord wordRecord) {
        wordsRepo.save(wordRecord);
        System.out.println("There are " + wordsRepo.count() + " words");
    }

    @GetMapping("all_words")
    public @ResponseBody
    List<WordRecord> all_words() {
        System.out.println("There are " + wordsRepo.count() + " words");
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
