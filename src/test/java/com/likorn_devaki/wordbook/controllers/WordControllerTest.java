package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.model.User;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.WordbookApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordControllerTest {

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveWord() {
        //TODO add a word for the user only if the user with the specified id exists
        Word word = new Word(1, "viis", "five");
        ResponseEntity<Word> responseEntity = restTemplate.postForEntity(
                "/save_word", word, Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Word savedWord = responseEntity.getBody();
        assertNotNull(savedWord);
    }

    @Test
    public void getAllWords() {
        // TODO generate sample token
        String token = "";
        // TODO pass the token to the url
        ResponseEntity<List<Word>> entity = restTemplate.exchange(
                "/words",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Word>>() {});
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<Word> words = entity.getBody();
        assertNotNull(words);
        assertTrue(!words.isEmpty());
    }

    @Test
    public void updateWord() {
        Word updatedWord = WordbookApplication.getSampleWord(4);
        updatedWord.setForeignWord("tervist");
        HttpEntity httpEntity = new HttpEntity<>(updatedWord);
        ResponseEntity<Word> responseEntity = restTemplate.exchange(
                "/update_word?word_id=" + updatedWord.getId(),
                HttpMethod.PUT,
                httpEntity,
                Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Word result = responseEntity.getBody();
        assertEquals(result, updatedWord);
    }

    @Test
    public void deleteWord() {
        Integer wordRecordId = WordbookApplication.getSampleWord(0).getId();
        ResponseEntity<Word> responseEntity = restTemplate.exchange(
                "/delete_word?word_id=" + wordRecordId,
                HttpMethod.DELETE,
                null,
                Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    public void addTag() {
        User user = WordbookApplication.getSampleUser(1);
        Tag tag = new Tag(user, "numbers");
    }
}