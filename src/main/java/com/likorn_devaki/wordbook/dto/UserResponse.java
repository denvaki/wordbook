package com.likorn_devaki.wordbook.dto;

import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import lombok.Builder;

import java.util.List;

@Builder
public class UserResponse {
    public String message;
    public String token;
    public Word word;
    public Tag tag;
    public List<Word> wordList;
    public List<Tag> tagList;
}

