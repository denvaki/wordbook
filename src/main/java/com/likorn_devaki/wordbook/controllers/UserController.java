package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.dto.UserToken;
import com.likorn_devaki.wordbook.model.User;
import com.likorn_devaki.wordbook.security.PasswordEncoder;
import com.likorn_devaki.wordbook.repos.UsersRepository;
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
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping(path = "register")
    public ResponseEntity createUser(@RequestBody User user) {
        System.out.println("registering a new user");
        if(user.getUsername() ==  null || user.getPassword() == null){
            return ResponseEntity.badRequest().body("Empty one or both of credentials fields");
        }
        user.setCreated(LocalDateTime.now());
        if (usersRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(String.format("Username %s already exist",  user.getUsername()));
        }
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "login")
    public ResponseEntity loginUser(@RequestBody User user) {
        User dbUser = usersRepository.findUserByUsername(user.getUsername());
        if (dbUser != null && PasswordEncoder.match(dbUser.getPassword(), user.getPassword()))
            return ResponseEntity.ok(UserToken.of(dbUser.getUsername(), new JWTProvider().createJWT(user.getUsername())));
        return  ResponseEntity.badRequest().body("Bad credentials");
    }

    @GetMapping(path = "all_users")
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepository.findAll());
    }
}
