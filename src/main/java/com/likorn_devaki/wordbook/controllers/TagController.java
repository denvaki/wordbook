package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.likorn_devaki.wordbook.JWT.JWTProvider.extractUserId;
import static com.likorn_devaki.wordbook.JWT.JWTProvider.invalidToken;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
@RestController
public class TagController {

    @Autowired
    private TagsRepository tagsRepository;
    private ResponseEntity<UserResponse> responseReLogIn =
            ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());

    @PostMapping(path = "save_tag")
    public ResponseEntity<UserResponse> save(
            @RequestBody Tag tag,
            HttpServletRequest request){
        Integer userId = extractUserId(request);
        if (userId == null)
            return responseReLogIn;
        tag.setUserId(userId);
        return  ResponseEntity.ok(UserResponse.builder().tag(tagsRepository.save(tag)).build());
    }

    @PutMapping(path = "update_tag")
    public ResponseEntity<UserResponse> update(@RequestBody Tag tag, HttpServletRequest request) {
        if (invalidToken(request))
            return responseReLogIn;
        return ResponseEntity.ok().body(UserResponse.builder().tag(tagsRepository.save(tag)).message("Word has been updated!").build());
    }

    @DeleteMapping(path = "delete_tag")
    public ResponseEntity<UserResponse> delete(@RequestParam(value = "tag_id") Integer tagId, HttpServletRequest request) {
        if (invalidToken(request))
            return responseReLogIn;
        tagsRepository.deleteById(tagId);
        return ResponseEntity.ok().body(UserResponse.builder().message("Word has been deleted!").build());
    }

    @GetMapping(path = "tags")
    public ResponseEntity findAll(HttpServletRequest request){
        Integer userId = extractUserId(request);
        if (userId == null)
            return responseReLogIn;
        return ResponseEntity.ok().body(UserResponse.builder().tagList(tagsRepository.findAllByUserId(userId)).build());
    }
}
