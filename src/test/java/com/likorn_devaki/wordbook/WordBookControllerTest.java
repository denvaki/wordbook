package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordBookControllerTest {

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveWord() { // check that a word is saved and produces a positive response
        //TODO add a word for the user only if the user with the specified id exists
        WordRecord testWord = new WordRecord(4, "kolm", "three");
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + WordBookController.SAVE_WORD_PATH, testWord, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertEquals(WordBookController.SUCCESS_WORD_SAVED, responseTransfer.getResponse());
    }

    @Test
    public void createUser() {
        // you need to update the username manually every time, otherwise th test won't pass as it won't be unique
        User newUser = new User("monkey1916", "1234");
        validateUserWithUniqueUsernameCreated(newUser);
        validateUserWithExistingUsernameNotCreated(newUser);
    }

    private void validateUserWithUniqueUsernameCreated(User user) {
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + WordBookController.CREATE_USER_PATH, user, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertEquals(WordBookController.SUCCESS_USER_CREATED, responseTransfer.getResponse());
    }

    private void validateUserWithExistingUsernameNotCreated(User user) {
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + WordBookController.CREATE_USER_PATH, user, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertEquals(WordBookController.ERROR_USERNAME_NOT_UNIQUE, responseTransfer.getResponse());
    }

    @Test
    public void getAllWords() { // check that the request returns some words
        ResponseEntity<List<WordRecord>> entity = restTemplate.exchange(
                "/" + WordBookController.ALL_WORDS_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WordRecord>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<WordRecord> wordRecords = entity.getBody();
        assertNotNull(wordRecords);
        assertTrue(wordRecords.size() > 0);
    }

    @Test
    public void getAllUsers() { // check that the request returns some users
        ResponseEntity<List<User>> entity = restTemplate.exchange(
                "/" + WordBookController.ALL_USERS_PATH,
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
    public void getAllWordsWhereUserId() { // check that the request returns some wordRecords for the selected user
        // there is some data added for the user with user_id=4
        String url = "/" + WordBookController.ALL_WORDS_WHERE_USER_ID + "?user_id=4";

        ResponseEntity<List<WordRecord>> entity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WordRecord>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<WordRecord> wordRecords = entity.getBody();
        assertNotNull(wordRecords);
        assertTrue(wordRecords.size() > 0);
    }
}