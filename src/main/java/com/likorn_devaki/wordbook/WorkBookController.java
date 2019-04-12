package com.likorn_devaki.wordbook;


import com.likorn_devaki.wordbook.DAOClasses.DAO;
import com.likorn_devaki.wordbook.Entity.UserRepo;
import com.likorn_devaki.wordbook.Entity.Users;
import com.likorn_devaki.wordbook.Entity.Words;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.Query;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequestMapping
@RestController
public class WorkBookController {

    private DAO dao = new DAO();

    @PostMapping(path = "register")
    public @ResponseBody List<Users> registration(@RequestBody Users user) {
        System.out.println(user);
        dao.save(user);
        return dao.getAllUsers();
    }

    @PostMapping(path = "save-word")
    public @ResponseBody List<Words> savingWord(@RequestBody Words word) {
        dao.save(word);
        return dao.getAllWordsOfUser(word.getUser_id());

    }

    @GetMapping("all-users")
    public @ResponseBody List<Users> greeting() {
        return dao.getAllUsers();

        /*return StreamSupport.stream(userRepo.findAll().spliterator(), false)
                .collect(Collectors.toList());*/
    }
}
