package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Integer> {
    boolean existsByUsername(String username);
    User findUserByUsername(String username);
}