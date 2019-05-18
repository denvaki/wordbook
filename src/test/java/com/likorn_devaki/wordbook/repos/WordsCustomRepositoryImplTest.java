package com.likorn_devaki.wordbook.repos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WordsCustomRepositoryImplTest {

    @Autowired
    private WordsRepository wordsRepository;

    @Test
    public void findWordsByParams() {
        wordsRepository.findWordsByParams(null, null, null, 2);
    }
}