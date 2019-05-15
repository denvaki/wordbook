package com.likorn_devaki.wordbook.Controllers;

import com.likorn_devaki.wordbook.Entities.Token;
import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping(path = "register")
    public ResponseEntity createUser(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Empty one or both of credentials fields");
        }
        user.setCreated(LocalDateTime.now());
        if (usersRepo.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body(String.format("Username %s already exist", user.getUsername()));
        }
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        usersRepo.save(user);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping(path = "login")
    public String loginUser(@RequestBody User user) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);


        User dbUser = usersRepo.findUserByUsernameAndPassword(user.getUsername(), user.getPassword());
        // TODO check that the password is correct
        String token = null;
        if (dbUser != null)
            //TODO encrypt the token with the private(?) key
            token = new Token(dbUser).toString();
        System.out.println(token);
        return token;
    }

    @GetMapping(path = "all_users")
    public List<User> getAllUsers() {
        return new ArrayList<>(usersRepo.findAll());
    }
}
