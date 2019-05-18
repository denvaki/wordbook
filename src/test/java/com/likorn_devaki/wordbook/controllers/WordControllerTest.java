package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.model.User;
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

    private static final String
            SAVE_WORD_PATH = "save_word",
            ALL_WORDS_PATH = "all_words",
            CREATE_USER_PATH = "create_user",
            ALL_USERS_PATH = "all_users",
            ALL_WORDS_WHERE_USER_ID_PATH = "all_words_where_user_id",
            UPDATE_WORD_PATH = "update_word",
            DELETE_WORD_PATH = "delete_word";

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveWord() {
        //TODO add a word for the user only if the user with the specified id exists
        Word word = new Word(1, "viis", "five");
        ResponseEntity<Word> responseEntity = restTemplate.postForEntity(
                "/" + SAVE_WORD_PATH, word, Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Word savedWord = responseEntity.getBody();
        assertNotNull(savedWord);
    }

    @Test
    public void userWithUniqueUsernameIsCreated() {
        User user = new User("meow", "meow");
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "/" + CREATE_USER_PATH, user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User createdUser = responseEntity.getBody();
        assertNotNull(createdUser);
    }

    @Test
    public void userWithExistingUsernameNotCreated() {
        User user = WordbookApplication.getSampleUserWithNullId(0);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "/" + CREATE_USER_PATH, user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User createdUser = responseEntity.getBody();
        assertNull(createdUser);
    }

    @Test
    public void getAllWords() {
        ResponseEntity<List<Word>> entity = restTemplate.exchange(
                "/" + ALL_WORDS_PATH,
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
    public void getAllUsers() {
        ResponseEntity<List<User>> entity = restTemplate.exchange(
                "/" + ALL_USERS_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<User> users = entity.getBody();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void getAllWordsWhereUserId() { // check that the request returns some words for the selected user
        ResponseEntity<List<Word>> entity = restTemplate.exchange(
                "/" + ALL_WORDS_WHERE_USER_ID_PATH + "/1",
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
                "/" + SAVE_WORD_PATH, word, Word.class);
        Word savedWord = responseEntity.getBody();

        // update word
        if (savedWord != null) {
            savedWord.setForeignWord("kuus");

            // update the word
            HttpEntity httpEntity = new HttpEntity<>(savedWord);
            responseEntity = restTemplate.exchange(
                    "/" + UPDATE_WORD_PATH + "/" + savedWord.getId(),
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
                "/" + DELETE_WORD_PATH + "/" + wordRecordId,
                HttpMethod.DELETE,
                null,
                Word.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}