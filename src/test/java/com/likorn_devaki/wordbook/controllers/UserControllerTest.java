package com.likorn_devaki.wordbook.controllers;

import com.likorn_devaki.wordbook.WordbookApplication;
import com.likorn_devaki.wordbook.model.User;
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
public class UserControllerTest {

    @LocalServerPort
    int port = 9090;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void userWithUniqueUsernameIsCreated() {
        User user = new User("meow", "meow");
        ResponseEntity responseEntity = restTemplate.postForEntity("/register", user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void userWithExistingUsernameNotCreated() {
        User user = WordbookApplication.getSampleUser(0);
        user.setId(null);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity("/register", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void correctCredentialsLoggedIn() {
        User user = new User("paktalin", "234");
        ResponseEntity responseEntity = restTemplate.postForEntity("/login", user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void wrongCredentialsFailToLogIn() {
        User user = new User("paktalin", "123");
        ResponseEntity responseEntity = restTemplate.postForEntity("/login", user, User.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
