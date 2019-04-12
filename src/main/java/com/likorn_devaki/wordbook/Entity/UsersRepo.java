package com.likorn_devaki.wordbook.Entity;

import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<User, Integer> {

    //boolean existsByUsername(String username);
}