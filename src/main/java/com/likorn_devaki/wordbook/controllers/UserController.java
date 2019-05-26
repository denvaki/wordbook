package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.User;
import com.likorn_devaki.wordbook.security.PasswordEncoder;
import com.likorn_devaki.wordbook.repos.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        if(user.getUsername() ==  null || user.getPassword() == null){
            return ResponseEntity.badRequest().body("Some credentials are missing");
        }
        user.setCreated(LocalDateTime.now());
        if (usersRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(
                    UserResponse.builder()
                    .message(String.format("Username %s already exists",  user.getUsername()))
                    .build()
            );
        }
        user.setPassword(PasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
        return ResponseEntity.ok().body(UserResponse.builder().message("User has been signed up!").build());
    }

    @PostMapping(path = "login")
    public ResponseEntity loginUser(@RequestBody User user) {
        User dbUser = usersRepository.findUserByUsername(user.getUsername());
        if (dbUser != null && PasswordEncoder.match(dbUser.getPassword(), user.getPassword()))
            return ResponseEntity.ok(UserResponse.builder().token(JWTProvider.createJWT(dbUser.getId())).build());
        return ResponseEntity.badRequest().body(UserResponse.builder().message("Bad credentials").build());
    }
}
