package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.model.Word;

import java.util.List;

public interface WordsCustomRepository {
    List<Word> findWordsByParams(String foreignWord, String translatedWord, String tag);

}
