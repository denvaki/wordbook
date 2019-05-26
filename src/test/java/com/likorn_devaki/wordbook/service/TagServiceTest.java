package com.likorn_devaki.wordbook.service;

import com.likorn_devaki.wordbook.WordbookApplication;
import com.likorn_devaki.wordbook.dto.UserResponse;
import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.Word;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void save() {
        Tag tag = new Tag(null, "fruits");
        ResponseEntity<UserResponse> responseEntity = tagService.save(tag, 2);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.tag, tag);
    }

    @Test
    public void update() {
        Tag tag = WordbookApplication.getSampleTag(0);
        tag.setName("numbeeeeers");
        ResponseEntity<UserResponse> responseEntity = tagService.update(tag);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.tag.getName(), "numbeeeeers");
    }

    @Test
    public void delete() {
        ResponseEntity<UserResponse> responseEntity = tagService.delete(3);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
    }

    @Test
    public void findAll() {
        ResponseEntity<UserResponse> responseEntity = tagService.findAll(1);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        UserResponse userResponse = responseEntity.getBody();
        assert userResponse != null;
        assertEquals(userResponse.tagList.size(), 2);
    }
}