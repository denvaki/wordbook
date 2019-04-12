package com.likorn_devaki.wordbook.DAOClasses;

import com.likorn_devaki.wordbook.Entity.UserRepo;
import com.likorn_devaki.wordbook.Entity.Users;
import com.likorn_devaki.wordbook.Entity.Words;
import com.likorn_devaki.wordbook.Entity.WordsRepo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

public class DAO {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepo userRepo;

    @Autowired
    WordsRepo wordsRepo;

    @Transactional
    public void save(Users user){
        em.persist(user);
    }

    @Transactional
    public void save(Words word){
        em.persist(word);
    }

    public List<Users> getAllUsers(){
        return em.createQuery("select u from users", Users.class).getResultList();
    }

    public List<Words> getAllWords(){
        return em.createQuery("select w from words", Words.class).getResultList();
    }

    public List<Words> getAllWordsOfUser(Integer userId){
        return em.createQuery("select w from words where w.user_id = :id", Words.class)
                .setParameter("id", userId).getResultList();
    }
}
