package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.WordbookApplication;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class TagControllerTest {

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void save() {
        Tag tag = new Tag(WordbookApplication.getSampleUser(0), "food");
        ResponseEntity<Tag> responseEntity = restTemplate.postForEntity(
                "/save_tag", tag, Tag.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Tag savedTag = responseEntity.getBody();
        assertNotNull(savedTag);
    }

    @Test
    public void findAll() {


    }
}