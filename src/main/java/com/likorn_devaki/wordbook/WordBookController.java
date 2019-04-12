package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entity.User;
import com.likorn_devaki.wordbook.Entity.UsersRepo;
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

    static final String  saveWordPath = "save_word",
                                getAllWordsPath = "all_words",
                                addUserPath = "add_user",
                                getAllUsersPath = "all_users";

    private final WordsRepo wordsRepo;
    private final UsersRepo usersRepo;

    @Autowired
    public WordBookController(WordsRepo wordsRepo, UsersRepo usersRepo) {
        this.wordsRepo = wordsRepo;
        this.usersRepo = usersRepo;
    }

    @PostMapping(path = saveWordPath)
    @ResponseBody
    public ResponseTransfer saveWord(@RequestBody WordRecord wordRecord) {
        wordsRepo.save(wordRecord);
        return new ResponseTransfer("The word \"" + wordRecord.getForeign_word() + "\" has been successfully added");
    }

    @PostMapping(path = addUserPath)
    public @ResponseBody
    ResponseTransfer addUser(@RequestBody User user) {
        usersRepo.save(user);
        return new ResponseTransfer("The user \"" + user.getUsername() + "\" has been successfully added");
    }

    @GetMapping(getAllWordsPath)
    public @ResponseBody
    List<WordRecord> getAllWords() {
        System.out.println("There are " + wordsRepo.count() + " words");
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(getAllUsersPath)
    public @ResponseBody
    List<User> getAllUsers() {
        System.out.println("There are " + usersRepo.count() + " users");
        return StreamSupport.stream(usersRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}