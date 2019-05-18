package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.model.Word;
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
        ResponseEntity<List<Word>> entity = restTemplate.exchange(
                "/all_words",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Word>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<Word> words = entity.getBody();
        assertNotNull(words);
        assertTrue(!words.isEmpty());
    }

    @Test
    public void getAllWordsWhereUserId() { // check that the request returns some words for the selected user
        ResponseEntity<List<Word>> entity = restTemplate.exchange(
                "/all_words_where_user_id/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Word>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<Word> words = entity.getBody();
        assertNotNull(words);
        assertTrue(words.size() > 0);
    }

    @Test
    public void updateWord() {
        // save a word
        Word word = new Word(1, "kus", "six");
        ResponseEntity<Word> responseEntity = restTemplate.postForEntity(
                "/save_word", word, Word.class);
        Word savedWord = responseEntity.getBody();

        // update word
        if (savedWord != null) {
            savedWord.setForeignWord("kuus");

            // update the word
            HttpEntity httpEntity = new HttpEntity<>(savedWord);
            responseEntity = restTemplate.exchange(
                    "/update_word/" + savedWord.getId(),
                    HttpMethod.PUT,
                    httpEntity,
                    Word.class);
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
            Word updatedWord = responseEntity.getBody();
            assertEquals(savedWord, updatedWord);
        }
    }

    @Test
    public void deleteWord() {
        Integer wordRecordId = WordbookApplication.getSampleWord(0).getId();
        ResponseEntity<Word> responseEntity = restTemplate.exchange(
                "/delete_word/" + wordRecordId,
                HttpMethod.DELETE,
                null,
                Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}