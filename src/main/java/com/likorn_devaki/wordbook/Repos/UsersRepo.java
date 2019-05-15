package com.likorn_devaki.wordbook.Repos;

import com.likorn_devaki.wordbook.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepo extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
}