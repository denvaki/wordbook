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

    public static final String  saveWordPath = "save_word",
                                getAllWordsPath = "all_words";

    @Autowired
    private WordsRepo wordsRepo;

    @PostMapping(path = saveWordPath)
    public @ResponseBody
    void saveWord(@RequestBody WordRecord wordRecord) {
        wordsRepo.save(wordRecord);
        Log.d("There are " + wordsRepo.count() + " words");
    }

    @GetMapping(getAllWordsPath)
    public @ResponseBody
    List<WordRecord> getAllWords() {
        System.out.println("There are " + wordsRepo.count() + " words");
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
