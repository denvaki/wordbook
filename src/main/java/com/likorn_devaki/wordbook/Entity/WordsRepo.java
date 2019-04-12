package com.likorn_devaki.wordbook.Entity;

import org.springframework.data.repository.CrudRepository;

public interface WordsRepo extends CrudRepository<Words, Integer> {

    //boolean existsByUsername(String username);
}

