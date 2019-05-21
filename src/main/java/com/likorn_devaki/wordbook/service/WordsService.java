package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.JWT.JWTProvider;
import com.likorn_devaki.wordbook.dto.ResponseToUsr;
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
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;
    @Autowired
    private TagsRepository tagsRepository;

    public ResponseEntity<ResponseToUsr> save(Word word, HttpServletRequest request) {
        String token = extractToken(request);
        if(token == null){
            return  ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in").build());
        }
        word.setCreated(LocalDateTime.now());
        word.setUserId(JWTProvider.getUserId(token));
        return ResponseEntity.ok().body(ResponseToUsr.builder().word(wordsRepository.save(word)).message("Word has been saved!").build());
    }

    public ResponseEntity<ResponseToUsr> findOne(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in").build());
        Optional<Word> word = wordsRepository.findById(id);
        if (word.isPresent()) return ResponseEntity.ok(ResponseToUsr.builder().word(word.get()).build());
        return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("No such word").build());
        }


    public ResponseEntity<ResponseToUsr> findAllByUsername(String foreignWord,
                                        String translatedWord,
                                        String tag,
                                        HttpServletRequest request) {
        String token = extractToken(request);
        if (token == null)
            return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in").build());
        Integer userId = JWTProvider.getUserId(token);
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag))
            return ResponseEntity.ok(ResponseToUsr.builder()
                    .wordList(wordsRepository.findWordsByParams(foreignWord, translatedWord, tag, userId))
                    .build());
        return ResponseEntity.ok(ResponseToUsr.builder().wordList(wordsRepository.findWordsByUserId(userId)).build());
    }

    public ResponseEntity<ResponseToUsr> update(Word word, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in").build());
        return ResponseEntity.ok().body(ResponseToUsr.builder().word(wordsRepository.save(word)).message("Word has been updated!").build());
    }

    public ResponseEntity addTag(Word word, Tag tag, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in"));
        word.addTag(tag);
        tag.addWord(word);
        ImmutablePair<Word, Tag> pair = new ImmutablePair<>(wordsRepository.save(word), tagsRepository.save(tag));
        return ResponseEntity.ok(pair);
    }

    public ResponseEntity<ResponseToUsr> delete(Integer id, HttpServletRequest request) {
        if (invalidToken(request))
            return ResponseEntity.badRequest().body(ResponseToUsr.builder().message("Please re-log in").build());
        wordsRepository.deleteById(id);
        return ResponseEntity.ok().body(ResponseToUsr.builder().message("Word has been deleted!").build());
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