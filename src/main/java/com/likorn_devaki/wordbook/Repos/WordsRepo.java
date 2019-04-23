package com.likorn_devaki.wordbook.Repos;

import com.likorn_devaki.wordbook.Entities.WordRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WordsRepo extends JpaRepository<WordRecord, Integer> {

    /*@Query("SELECT w FROM WordRecord w WHERE w.user_id = ?1")
    List<WordRecord> findAllByUser_id(Integer userId);*/

    List<WordRecord>findAllByUserId(Integer userId);
}
