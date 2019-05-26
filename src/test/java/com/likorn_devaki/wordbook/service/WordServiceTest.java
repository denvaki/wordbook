package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.WordbookApplication;
import com.likorn_devaki.wordbook.dto.UserResponse;
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
@SpringBootTest()
public class WordServiceTest {

    @Autowired
    private WordService wordService;

    @Test
    public void save() {
        Word word = new Word(null, "viis", "five");
        ResponseEntity<UserResponse> responseEntity = wordService.save(word, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.word, word);
    }

    @Test
    public void findOne() {
        ResponseEntity<UserResponse> responseEntity = wordService.findOne(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.word.getForeignWord(), "kaks");
    }

    @Test
    public void findAllByUserId() {
        ResponseEntity<UserResponse> responseEntity = wordService.findAllByUserId(null, null, null, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.wordList.size(), 3);
    }

    @Test
    public void update() {
        Word word = WordbookApplication.getSampleWord(0);
        word.setTranslatedWord("Twoooo");
        ResponseEntity<UserResponse> responseEntity = wordService.update(word);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.word.getTranslatedWord(), "Twoooo");
    }

    @Test
    public void addTag() {
        Word word = WordbookApplication.getSampleWord(2);
        ResponseEntity<UserResponse> responseEntity = wordService.addTag(word, 1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertTrue(userResponse.word.getTagIds().contains(1));
    }

    @Test
    public void delete() {
        ResponseEntity<UserResponse> responseEntity = wordService.delete(4);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
    }
}