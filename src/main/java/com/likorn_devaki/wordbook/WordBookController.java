package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WordBookController {

    static final String // paths to mappings
            saveWordPath = "save_word",
            getAllWordsPath = "all_words",
            createUser = "create_user",
            getAllUsersPath = "all_users",
            getAllWordsWhereUserIdPath = "get_all_words_where_user_id";

    static final String // response messages
            wordSavedSuccess = "Congratulations! The word has been saved",
            userCreatedSuccess = "Congratulations! The user has been created!";

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
        return new ResponseTransfer(wordSavedSuccess);
    }

    @PostMapping(path = createUser)
    @ResponseBody
    public ResponseTransfer addUser(@RequestBody User user) {
        //TODO catch exception with user uniqueness
        usersRepo.save(user);
        return new ResponseTransfer(userCreatedSuccess);
    }

    @GetMapping(path = getAllWordsPath)
    @ResponseBody
    public List<WordRecord> getAllWords() {
        return StreamSupport.stream(wordsRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = getAllUsersPath)
    @ResponseBody
    public List<User> getAllUsers() {
        return StreamSupport.stream(usersRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping(path = getAllWordsWhereUserIdPath)
    @ResponseBody
    public List<WordRecord> getAllWordsWhereUserId(@RequestParam String user_id) {
        return wordsRepo.findAllByUser_id(Integer.parseInt(user_id));
    }
}