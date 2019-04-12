package com.likorn_devaki.wordbook.Entity;

import org.springframework.data.repository.CrudRepository;

public  interface WordsRepo extends CrudRepository<WordRecord, Integer> {

    //boolean existsByUsername(String username);
}
