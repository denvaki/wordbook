package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.WordbookApplication;
import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordsServiceTest {

    @Autowired
    private WordsService wordsService;

    @Test
    public void addTag() {
        Word word = WordbookApplication.getSampleWord(2);
        ResponseEntity<UserResponse> responseEntity = wordsService.addTag(word, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertTrue(userResponse.word.getTagIds().contains(1));
    }
}