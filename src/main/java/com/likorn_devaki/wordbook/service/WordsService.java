package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;

    public ResponseEntity<UserResponse> save(Word word, Integer userId) {
        word.setCreated(LocalDateTime.now().toString());
        word.setUserId(userId);
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("Word has been saved!").build());
    }

    public ResponseEntity<UserResponse> findOne(Integer id) {
        Optional<Word> word = wordsRepository.findById(id);
        if (word.isPresent()) return ResponseEntity.ok(UserResponse.builder().word(word.get()).build());
        return ResponseEntity.badRequest().body(UserResponse.builder().message("No such word").build());
    }


    public ResponseEntity<UserResponse> findAllByUserId(String foreignWord,
                                                        String translatedWord,
                                                        String tag,
                                                        Integer userId) {
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag))
            return ResponseEntity.ok(UserResponse.builder()
                    .wordList(wordsRepository.findWordsByParams(foreignWord, translatedWord, tag, userId))
                    .build());
        return ResponseEntity.ok(UserResponse.builder().wordList(wordsRepository.findWordsByUserId(userId)).build());
    }

    public ResponseEntity<UserResponse> update(Word word) {
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("Word has been updated!").build());
    }

    public ResponseEntity<UserResponse> addTag(Word word, Integer tagId) {
        word.addTag(tagId);
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("The tag has been added!").build());
    }

    public ResponseEntity<UserResponse> delete(Integer id) {
        wordsRepository.deleteById(id);
        return ResponseEntity.ok().body(UserResponse.builder().message("Word has been deleted!").build());
    }

}