package com.likorn_devaki.wordbook.Entity;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<Users, Integer> {

    //boolean existsByUsername(String username);
}
