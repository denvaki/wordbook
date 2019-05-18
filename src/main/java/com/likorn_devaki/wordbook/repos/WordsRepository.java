package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordsRepository extends JpaRepository<Word, Integer>, WordsCustomRepository {
    public List<Word> findWordsByUserId(String userId);
}
