package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.PasswordEncoder.PasswordEncoder;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import com.likorn_devaki.wordbook.DTO.UserCredentials;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class WordBookController {

    @Autowired
    private WordsRepo wordsRepo;
    @Autowired
    private UsersRepo usersRepo;

    //@Transactional
    @PostMapping(path = "save_word")
    public WordRecord saveWord(@RequestBody WordRecord wordRecord){
        wordRecord.setCreated(LocalDateTime.now().toString());
        return wordsRepo.save(wordRecord);
    }

    @PostMapping(path = "register")
    public ResponseEntity createUser(@RequestBody User user) {
        if(user.getUsername() ==  null || user.getPassword() == null){
            return ResponseEntity.badRequest().body("Empty one or both of credentials fields");
        }
        user.setCreated(LocalDateTime.now());
        if (usersRepo.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(String.format("Username %s already exist",  user.getUsername()));
        }
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        usersRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping(path = "all_words")
    public List<WordRecord> getAllWords() {
        System.out.println(new ArrayList<>(wordsRepo.findAll()));
        return new ArrayList<>(wordsRepo.findAll());
    }

    @GetMapping(path = "all_users")
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepo.findAll());
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