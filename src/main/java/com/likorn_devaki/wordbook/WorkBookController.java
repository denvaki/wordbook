package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.Entity.UserRepo;
import com.likorn_devaki.wordbook.Entity.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping
public class WorkBookController {

    @Autowired
    private UserRepo userRepo;

    @PostMapping(path = "test")
    public @ResponseBody String test(@RequestBody Users users){
        userRepo.save(users);
        return userRepo.findAll().toString();
    }

    @GetMapping("greeting")
    public @ResponseBody String greeting(){

        return userRepo.findAll().toString();
    }
}
