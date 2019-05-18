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
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "/create_user", user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User createdUser = responseEntity.getBody();
        assertNotNull(createdUser);
    }

    @Test
    public void userWithExistingUsernameNotCreated() {
        User user = WordbookApplication.getSampleUser(0);
        user.setId(null);
        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "/create_user", user, User.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User createdUser = responseEntity.getBody();
        assertNull(createdUser);
    }

    @Test
    public void getAllUsers() {
        ResponseEntity<List<User>> entity = restTemplate.exchange(
                "/all_users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<User>>() {
                });
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        List<User> users = entity.getBody();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }
}
