package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    @Autowired
    private TagsRepository tagsRepository;

    public ResponseEntity<UserResponse> save(Tag tag, Integer userId){
        tag.setUserId(userId);
        return  ResponseEntity.ok(UserResponse.builder().tag(tagsRepository.save(tag)).message("Tag has been saved!").build());
    }

    public ResponseEntity<UserResponse> update(Tag tag) {
        return ResponseEntity.ok().body(UserResponse.builder().tag(tagsRepository.save(tag)).message("Tag has been updated!").build());
    }

    public ResponseEntity<UserResponse> delete(Integer tagId) {
        tagsRepository.deleteById(tagId);
        return ResponseEntity.ok().body(UserResponse.builder().message("Tag has been deleted!").build());
    }

    public ResponseEntity<UserResponse> findAll(Integer userId){
        return ResponseEntity.ok().body(UserResponse.builder().tagList(tagsRepository.findAllByUserId(userId)).build());
    }
}
