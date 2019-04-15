package com.likorn_devaki.wordbook.Controllers;


import com.likorn_devaki.wordbook.DataTransferObject.ResponseTransfer;
import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WordBookController {

    /*static final String // paths to mappings
            saveWordPath = "save_word",
            getAllWordsPath = "all_words",
            createUser = "create_user",
            getAllUsersPath = "all_users",
            getAllWordsWhereUserIdPath = "get_all_words_where_user_id";

    static final String // response messages
            wordSavedSuccess = "Congratulations! The word has been saved",
            userCreatedSuccess = "Congratulations! The user has been created!";*/

    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private UsersRepo usersRepo;

    /*@Autowired
    public WordBookController(WordsRepo wordsRepo, UsersRepo usersRepo) {
        this.wordsRepo = wordsRepo;
        this.usersRepo = usersRepo;
    }*/

    @PostMapping(path = "save_word")
    @ResponseBody
    public ResponseTransfer saveWord(@RequestBody WordRecord wordRecord) {
        wordRecord.setCreated(LocalDateTime.now().toString());
        wordsRepo.save(wordRecord);
        return new ResponseTransfer("The word has been saved");
    }

    @PostMapping(path = "create_user")
    @ResponseBody
    public ResponseTransfer addUser(@RequestBody User user) {
        user.setCreated(LocalDateTime.now().toString());
        usersRepo.save(user);
        return new ResponseTransfer("The user has been created!");
    }

    @GetMapping(path = "all_words")
    @ResponseBody
    public List<WordRecord> getAllWords() {
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "all_users")
    @ResponseBody
    public List<User> getAllUsers() {
        return StreamSupport.stream(usersRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = "get_words_by_user_id/{user_id}")
    @ResponseBody
    public List<WordRecord> getAllWordsByUserId(@PathVariable String user_id) {
        return wordsRepo.findAllByUser_id(Integer.parseInt(user_id));
    }

}