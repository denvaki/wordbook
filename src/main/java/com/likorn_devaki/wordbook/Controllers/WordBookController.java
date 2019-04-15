package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequestMapping
@RestController
public class WordBookController {

    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private UsersRepo usersRepo;

    @PostMapping(path = "save_word")
    @ResponseBody
    public WordRecord saveWord(@RequestBody WordRecord wordRecord) {
        wordRecord.setCreated(LocalDateTime.now().toString());
        return wordsRepo.save(wordRecord);
    }

    @PostMapping(path = "create_user")
    @ResponseBody
    public User createUser(@RequestBody User user) {
        user.setCreated(LocalDateTime.now().toString());
        try {
            return usersRepo.save(user);
        } catch (DataIntegrityViolationException e) {
            //TODO notify the user that the username is not unique
            return null;
        }
    }

    @GetMapping(path = "all_words")
    @ResponseBody
    public List<WordRecord> getAllWords() {
        return new ArrayList<>(wordsRepo.findAll());
    }

    @GetMapping(path = "all_users")
    @ResponseBody
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepo.findAll());
    }

    @GetMapping(path = "all_words_where_user_id/{user_id}")
    @ResponseBody
    public List<WordRecord> getAllWordsByUserId(@PathVariable String user_id) {
        return wordsRepo.findAllByUser_id(Integer.parseInt(user_id));
    }
}