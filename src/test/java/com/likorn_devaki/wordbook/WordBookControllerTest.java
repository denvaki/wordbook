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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WordBookControllerTest {

    private static final String
            SAVE_WORD_PATH = "save_word",
            ALL_WORDS_PATH = "all_words",
            CREATE_USER_PATH = "create_user",
            ALL_USERS_PATH = "all_users",
            ALL_WORDS_WHERE_USER_ID_PATH = "all_words_where_user_id";

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void saveWord() {
        //TODO add a word for the user only if the user with the specified id exists
        WordRecord wordRecord = new WordRecord(1, "viis", "five");
        ResponseEntity<WordRecord> responseEntity = restTemplate.postForEntity(
                "/" + SAVE_WORD_PATH, wordRecord, WordRecord.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        WordRecord savedWordRecord = responseEntity.getBody();
        assertNotNull(savedWordRecord);
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
        ResponseEntity<List<WordRecord>> entity = restTemplate.exchange(
                "/" + ALL_WORDS_PATH,
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
    public void getAllWordsWhereUserId() { // check that the request returns some wordRecords for the selected user
        String url = "/" + ALL_WORDS_WHERE_USER_ID_PATH + "/1";

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