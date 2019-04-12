package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entity.UserRepo;
import com.likorn_devaki.wordbook.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WorkBookController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping(path = "test")
    public @ResponseBody
    List<Users> test(@RequestBody Users users) {
        userRepo.save(users);

        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @GetMapping("greeting")
    public @ResponseBody
    List<Users> greeting() {

        return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }
}
