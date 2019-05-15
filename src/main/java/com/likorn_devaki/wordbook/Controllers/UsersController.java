package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.Token;
import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.PasswordEncoder.PasswordEncoder2;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class UsersController {

    @Autowired
    private UsersRepo usersRepo;

    @PostMapping(path = "register")
    public ResponseEntity createUser(@RequestBody User user) {
        System.out.println("registering a new user");
        if(user.getUsername() ==  null || user.getPassword() == null){
            return ResponseEntity.badRequest().body("Empty one or both of credentials fields");
        }
        user.setCreated(LocalDateTime.now());
        if (usersRepo.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(String.format("Username %s already exist",  user.getUsername()));
        }
        user.setPassword(PasswordEncoder2.encode(user.getPassword()));
        usersRepo.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(path = "login")
    public Token loginUser(@RequestBody User user) {
        User dbUser = usersRepo.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (dbUser != null)
            return new Token(user);
        return  null;
    }

    @GetMapping(path = "all_users")
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepo.findAll());
    }
}
