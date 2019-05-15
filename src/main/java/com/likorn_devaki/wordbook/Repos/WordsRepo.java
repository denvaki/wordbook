package com.likorn_devaki.wordbook.Repos;

import com.likorn_devaki.wordbook.entities.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordsRepo extends JpaRepository<Word, Integer> {

    /*@Query("SELECT w FROM Word w WHERE w.user_id = ?1")
    List<Word> findAllByUser_id(Integer userId);*/

    List<Word>findAllByUserId(Integer userId);
}
