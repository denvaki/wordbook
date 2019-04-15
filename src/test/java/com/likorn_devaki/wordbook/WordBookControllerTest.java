package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.Controllers.WordBookController;
import com.likorn_devaki.wordbook.DataTransferObject.ResponseTransfer;
import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordBookControllerTest {

    static final String // paths to mappings
            saveWordPath = "save_word",
            getAllWordsPath = "all_words",
            createUser = "create_user",
            getAllUsersPath = "all_users",
            getWordsByUserIdPath = "get_all_words_where_user_id";

    static final String // response messages
            wordSavedSuccess = "The word has been saved",
            userCreatedSuccess = "The user has been created!";

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveWord() { // check that a word is saved and produces a positive response
        //TODO add a word for the user only if the user with the specified id exists
        WordRecord testWord = new WordRecord(4, "kolm", "three");
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + saveWordPath, testWord, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertEquals(wordSavedSuccess, responseTransfer.getResponse());
    }

    @Test
    public void createUser() {
        // you need to update the username manually every time, otherwise th test won't pass as it won't be unique
        User newUser = new User("monkey1902", "1234");
        newUser.setCreated(LocalDateTime.now().toString());
        validateUserWithUniqueUsernameCreated(newUser);
        validateUserWithExistingUsernameNotCreated(newUser);
    }

    private void validateUserWithUniqueUsernameCreated(User user) {
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + createUser, user, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertEquals(userCreatedSuccess, responseTransfer.getResponse());
    }

    private void validateUserWithExistingUsernameNotCreated(User user) {
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + createUser, user, ResponseTransfer.class);
        assertNull(responseTransfer.getResponse());
    }

    @Test
    public void getAllWords() { // check that the request returns some words
        ResponseEntity<List<WordRecord>> entity = restTemplate.exchange(
                "/" + getAllWordsPath,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WordRecord>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<WordRecord> wordRecords = entity.getBody();
        assertNotNull(wordRecords);
        assertTrue(!wordRecords.isEmpty());
    }

    @Test
    public void getAllUsers() { // check that the request returns some users
        ResponseEntity<List<User>> entity = restTemplate.exchange(
                "/" + getAllUsersPath,
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
        String url = "/" + getWordsByUserIdPath + "/4";

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