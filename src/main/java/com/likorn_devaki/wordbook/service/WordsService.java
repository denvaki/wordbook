package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;
    @Autowired
    private TagsRepository tagsRepository;

    public Word save(Word word, HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null)
            return null;
        word.setCreated(LocalDateTime.now().toString());
        word.setUserId(JWTProvider.getUserId(token));
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
        Integer userId = JWTProvider.getUserId(token);
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag))
            return ResponseEntity.ok(wordsRepository.findWordsByParams(foreignWord, translatedWord, tag, userId));
        return ResponseEntity.ok(wordsRepository.findWordsByUserId(userId));
    }

    public Word update(Word word, HttpServletRequest request) {
        if (invalidToken(request))
            return null;
        return wordsRepository.save(word);
    }

    public ResponseEntity addTag(Word word, Tag tag, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body("Bad credentials");
        word.addTag(tag);
        tag.addWord(word);
        ImmutablePair<Word, Tag> pair = new ImmutablePair<>(wordsRepository.save(word), tagsRepository.save(tag));
        return ResponseEntity.ok(pair);
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