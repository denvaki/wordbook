package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.likorn_devaki.wordbook.JWT.JWTProvider.extractToken;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class TagController {

    @Autowired
    private TagsRepository tagsRepository;

    @PostMapping(path = "save_tag")
    public ResponseEntity<UserResponse> save(
            @RequestBody Tag tag,
            HttpServletRequest request){
        String token = extractToken(request);
        if (token == null)
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        Integer userId = JWTProvider.getUserId(token);
        tag.setUserId(userId);
        return  ResponseEntity.ok(UserResponse.builder().tag(tagsRepository.save(tag)).build());
    }

    @GetMapping(path = "tags")
    public ResponseEntity findAll(HttpServletRequest request){
        // TODO return tagIds of the current user only
        String token = extractToken(request);
        if (token == null)
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        Integer userId = JWTProvider.getUserId(token);
        return ResponseEntity.ok().body(UserResponse.builder().tagList(tagsRepository.findAllByUserId(userId)).build());
    }
}
