package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.UsersRepository;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public Word save(Word word) {
        word.setCreated(LocalDateTime.now().toString());
        return wordsRepository.save(word);
    }

    public Word findOne(Integer id) {
        return wordsRepository.findById(id).orElseThrow(this::badRequest);
    }

    public List<Word> findAllByUsername(String foreignWord, String translatedWord, String tag, String username) {
        if (isNotBlank(foreignWord) || isNotBlank(translatedWord) || isNotBlank(tag)) {
            return wordsRepository.findWordsByParams(foreignWord, translatedWord, tag);
        }
        return new ArrayList<>(wordsRepository.
                findWordsByUserId(usersRepository.findUserByUsername(username).getUsername()));
    }

    public Word update(Integer word_id, Word word) {
        word.setId(word_id);
        return wordsRepository.save(word);
    }

    public void delete(Integer id) {
        wordsRepository.deleteById(id);
    }

    private ResponseStatusException badRequest() {
        return new ResponseStatusException(BAD_REQUEST, "id doesnt exist");
    }
}