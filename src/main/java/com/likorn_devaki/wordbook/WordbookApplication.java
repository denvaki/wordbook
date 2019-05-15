package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.entities.User;
import com.likorn_devaki.wordbook.entities.Word;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WordbookApplication {

    private static final List<User> sampleUsers = Arrays.asList(
            new User("devaki", "123"),
            new User("paktalin", "234"));

    private static final List<Word> SAMPLE_WORDS = Arrays.asList(
            new Word(0, "kaks", "two"),
            new Word(0, "kolm", "three"),
            new Word(0, "neli", "four"),
            new Word(1, "tere", "hello"),
            new Word(1, "head aega", "goodbye"));

    public static void main(String[] args) {
        SpringApplication.run(WordbookApplication.class, args);
    }

    @Bean
    public CommandLineRunner initWords(WordsRepo repository) {
        String created = LocalDateTime.now().toString();
        SAMPLE_WORDS.forEach(w -> w.setCreated(created));
        return (args) -> repository.saveAll(SAMPLE_WORDS);
    }

    @Bean
    public CommandLineRunner initUsers(UsersRepo repository) {
        return (args) -> {
            sampleUsers.forEach(u -> u.setCreated(LocalDateTime.now()));
            repository.saveAll(sampleUsers);
        };
    }

    public static User getSampleUserWithNullId(int index) {
        User sampleUser = sampleUsers.get(index);
        sampleUser.setId(null);
        return sampleUser;
    }

    public static Word getSampleWord(int index) {
        return SAMPLE_WORDS.get(index);
    }
}
