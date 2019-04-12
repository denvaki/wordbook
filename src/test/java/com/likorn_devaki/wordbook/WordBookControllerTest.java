package com.likorn_devaki.wordbook;

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
    public void saveWord() { }

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
}