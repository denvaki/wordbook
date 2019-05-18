package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.UsersRepository;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;

    @Autowired
    private UsersRepository usersRepository;

    public Word save(Word word, HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null)
            return null;
        word.setCreated(LocalDateTime.now().toString());
        Integer userId = usersRepository.findUserByUsername(JWTProvider.getUsername(token)).getId();
        word.setUserId(userId);
        return wordsRepository.save(word);
    }

    public Word findOne(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return null;
        return wordsRepository.findById(id).orElseThrow(this::badRequest);
    }

    public ResponseEntity findAllByUsername(String foreignWord,
                                        String translatedWord,
                                        String tag,
                                        HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null)
            return ResponseEntity.badRequest().body("Bad credentials");
        // TODO pack userId into the token instead of username
        Integer userId = usersRepository.findUserByUsername(JWTProvider.getUsername(token)).getId();
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag))
            return ResponseEntity.ok(wordsRepository.findWordsByParams(foreignWord, translatedWord, tag, userId));
        return ResponseEntity.ok(wordsRepository.findWordsByUserId(userId));
    }

    public Word update(Word word, HttpServletRequest request) {
        if (invalidToken(request))
            return null;
        return wordsRepository.save(word);
    }

    public ResponseEntity delete(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body("Bad credentials");
        wordsRepository.deleteById(id);
        return ResponseEntity.ok().build();    }

    private ResponseStatusException badRequest() {
        return new ResponseStatusException(BAD_REQUEST, "id doesnt exist");
    }

    private boolean invalidToken(HttpServletRequest request) {
        return extractToken(request) == null;
    }

    private String extractToken(HttpServletRequest request) {
        String token = JWTProvider.resolveToken(request).orElse(null);
        if (JWTProvider.invalidToken(token))
            return null;
        return token;
    }
}