package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WordBookController {

    static final String // paths to mappings
            SAVE_WORD_PATH = "save_word",
            ALL_WORDS_PATH = "all_words",
            CREATE_USER_PATH = "create_user",
            ALL_USERS_PATH = "all_users",
            ALL_WORDS_WHERE_USER_ID = "all_words_where_user_id";

    static final String // response messages
            SUCCESS_WORD_SAVED = "Congratulations! The word has been saved",
            SUCCESS_USER_CREATED = "Congratulations! The user has been created!",
            ERROR_USERNAME_NOT_UNIQUE = "The username is not unique!";

    private final WordsRepo wordsRepo;
    private final UsersRepo usersRepo;

    @Autowired
    public WordBookController(WordsRepo wordsRepo, UsersRepo usersRepo) {
        this.wordsRepo = wordsRepo;
        this.usersRepo = usersRepo;
    }

    @PostMapping(path = SAVE_WORD_PATH)
    @ResponseBody
    public ResponseTransfer saveWord(@RequestBody WordRecord wordRecord) {
        wordsRepo.save(wordRecord);
        return new ResponseTransfer(SUCCESS_WORD_SAVED);
    }

    @PostMapping(path = CREATE_USER_PATH)
    @ResponseBody
    public ResponseTransfer addUser(@RequestBody User user) {
        ResponseTransfer responseTransfer;
        try {
            usersRepo.save(user);
            responseTransfer = new ResponseTransfer(SUCCESS_USER_CREATED);
        } catch (DataIntegrityViolationException e) {
            responseTransfer = new ResponseTransfer(ERROR_USERNAME_NOT_UNIQUE);
        }
        return responseTransfer;
    }

    @GetMapping(path = ALL_WORDS_PATH)
    @ResponseBody
    public List<WordRecord> getAllWords() {
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = ALL_USERS_PATH)
    @ResponseBody
    public List<User> getAllUsers() {
        return StreamSupport.stream(usersRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = ALL_WORDS_WHERE_USER_ID)
    @ResponseBody
    public List<WordRecord> getAllWordsWhereUserId(@RequestParam String user_id) {
        return wordsRepo.findAllByUser_id(Integer.parseInt(user_id));
    }
}