package com.likorn_devaki.wordbook.repos;

import com.likorn_devaki.wordbook.model.Word;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class WordsCustomRepositoryImpl implements WordsCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Word> findWordsByParams(String foreignWord, String translatedWord, String tag, Integer userId) {

        Query query = entityManager.createNativeQuery("" +
                        "SELECT *\n" +
                        "FROM words \n" +
                        "WHERE true \n" +
                        "userId = userId\n" +
                        (isNotBlank(foreignWord) ? "and lower(foreignWord) LIKE :foreignWord \n" : "") +
                        (isNotBlank(translatedWord) ? "and lower(translatedWord) LIKE :translatedWord \n" : "") +
                        (isNotBlank(tag) ? "and lower(tag) LIKE :tag \n" : "")
                , Word.class);

        if (isNotBlank(foreignWord)) {
            query = query.setParameter("foreignWord", "%" + foreignWord.toLowerCase() + "%");
        }
        if (isNotBlank(translatedWord)) {
            query = query.setParameter("translatedWord", "%" + translatedWord.toLowerCase() + "%");
        }
        if (isNotBlank(tag)) {
            query = query.setParameter("tag", "%" + tag.toLowerCase() + "%");
        }
        return (List<Word>) query.getResultList();
    }
}
