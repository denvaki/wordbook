package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.Entity.User;
import com.likorn_devaki.wordbook.Entity.WordRecord;
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

    @Autowired
        private TestRestTemplate restTemplate;

    @LocalServerPort
    int port = 9090;

    @Test
    public void saveWord() {
        WordRecord testWord = new WordRecord(4, "kevad", "spring");
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + WordBookController.saveWordPath, testWord, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertNotNull(responseTransfer.getResponse());
    }

    @Test
    public void addUser() {
        User testUser = new User("monkey1893", "1234");
        ResponseTransfer responseTransfer = restTemplate.postForObject("/" + WordBookController.addUserPath, testUser, ResponseTransfer.class);
        assertNotNull(responseTransfer);
        assertNotNull(responseTransfer.getResponse());
    }

    @Test
    public void getAllWords() {
        ResponseEntity<List<WordRecord>> entity = restTemplate.exchange(
                "/" + WordBookController.getAllWordsPath,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<WordRecord>>(){});
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<WordRecord> wordRecords = entity.getBody();
        assertNotNull(wordRecords);
        assertEquals("tere", wordRecords.get(0).getForeign_word());
    }

    @Test
    public void getAllUsers() {
        ResponseEntity<List<User>> entity = restTemplate.exchange(
                "/" + WordBookController.getAllUsersPath,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>(){});
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<User> users = entity.getBody();
        assertNotNull(users);
        assertEquals("paktalin", users.get(0).getUsername());
    }
}