package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.entities.Word;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;

    public Word save(Word word) {
        word.setCreated(LocalDateTime.now().toString());
        return wordsRepository.save(word);
    }

    public List<Word> getAll() {
        return new ArrayList<>(wordsRepository.findAll());
    }

    public Word update(Integer word_id, Word word) {
        word.setId(word_id);
        return wordsRepository.save(word);
    }

    public void delete(Integer id) {
        wordsRepository.deleteById(id);
    }
}