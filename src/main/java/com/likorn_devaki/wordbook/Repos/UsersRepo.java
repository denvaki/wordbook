package com.likorn_devaki.wordbook.Repos;

import com.likorn_devaki.wordbook.Entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepo extends CrudRepository<User, Integer> {
}