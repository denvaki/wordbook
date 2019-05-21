package com.likorn_devaki.wordbook.dto;

import com.likorn_devaki.wordbook.model.Word;
import lombok.Builder;

import java.util.List;

@Builder
public class ResponseToUsr {
    public String message;
    public String token;
    public Word word;
    public List<Word> wordList;
}

