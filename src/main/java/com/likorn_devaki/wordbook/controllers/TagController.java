package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class TagController {

    @Autowired
    private TagsRepository tagsRepository;

    @PostMapping(path = "save_tag")
    public Tag save(@RequestBody Tag tag){
        return tagsRepository.save(tag);
    }

    @GetMapping(path = "tags")
    public ResponseEntity findAll(){
        // TODO return tagIds of the current user only
        return ResponseEntity.ok(tagsRepository.findAll());
    }
}
