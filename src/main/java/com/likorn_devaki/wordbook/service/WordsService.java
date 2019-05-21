package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.likorn_devaki.wordbook.JWT.JWTProvider.extractToken;
import static com.likorn_devaki.wordbook.JWT.JWTProvider.invalidToken;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;

    public ResponseEntity<UserResponse> save(Word word, HttpServletRequest request) {
        String token = extractToken(request);
        if(token == null){
            return  ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        }
        word.setCreated(LocalDateTime.now());
        word.setUserId(JWTProvider.getUserId(token));
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("Word has been saved!").build());
    }

    public ResponseEntity<UserResponse> findOne(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        Optional<Word> word = wordsRepository.findById(id);
        if (word.isPresent()) return ResponseEntity.ok(UserResponse.builder().word(word.get()).build());
        return ResponseEntity.badRequest().body(UserResponse.builder().message("No such word").build());
        }


    public ResponseEntity<UserResponse> findAllByUsername(String foreignWord,
                                                          String translatedWord,
                                                          String tag,
                                                          HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null)
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        Integer userId = JWTProvider.getUserId(token);
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag))
            return ResponseEntity.ok(UserResponse.builder()
                    .wordList(wordsRepository.findWordsByParams(foreignWord, translatedWord, tag, userId))
                    .build());
        return ResponseEntity.ok(UserResponse.builder().wordList(wordsRepository.findWordsByUserId(userId)).build());
    }

    public ResponseEntity<UserResponse> update(Word word, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("Word has been updated!").build());
    }

    public ResponseEntity<UserResponse> addTag(Word word, String tagId, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        word.addTag(tagId);
        return ResponseEntity.ok().body(UserResponse.builder().word(wordsRepository.save(word)).message("The tag has been added!").build());
    }

    public ResponseEntity<UserResponse> delete(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(UserResponse.builder().message("Please re-log in").build());
        wordsRepository.deleteById(id);
        return ResponseEntity.ok().body(UserResponse.builder().message("Word has been deleted!").build());
    }

}