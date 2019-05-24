package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.model.Tag;
import com.likorn_devaki.wordbook.model.User;
import com.likorn_devaki.wordbook.model.Word;
import com.likorn_devaki.wordbook.repos.TagsRepository;
import com.likorn_devaki.wordbook.repos.UsersRepository;
import com.likorn_devaki.wordbook.repos.WordsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WordbookApplication {

    private static final List<User> SAMPLE_USERS = Arrays.asList(
            new User("devaki", "$2a$10$iQP5PlKDGykcLeg55tQMZ.bKWcwxZ4DiFgWaYQbePI6RrmoWK3UOm"),
            new User("paktalin", "$2a$10$MEBVisq.DwSS1finjdrf.eEmqwD4SiOOzU.XRp15MCMWzCLN4HR32"));

    private static final List<Word> SAMPLE_WORDS = Arrays.asList(
            new Word(1, "kaks", "two"),
            new Word(1, "kolm", "three"),
            new Word(1, "neli", "four"),
            new Word(2, "tere", "hello"),
            new Word(2, "head aega", "goodbye"));

    private static final List<Tag> SAMPLE_TAGS = Arrays.asList(
            new Tag(1, "numbers"),
            new Tag(1, "verbs"));

    public static void main(String[] args) {
        SpringApplication.run(WordbookApplication.class, args);
    }


    @Bean
    public CommandLineRunner initUsers(UsersRepository repository) {
        return (args) -> {
            SAMPLE_USERS.forEach(u -> u.setCreated(LocalDateTime.now()));
            repository.saveAll(SAMPLE_USERS);
        };
    }

    @Bean
    public CommandLineRunner initWords(WordsRepository repository) {
        SAMPLE_WORDS.forEach(w -> w.setCreated(LocalDateTime.now().toString()));
        return (args) -> repository.saveAll(SAMPLE_WORDS);
    }

    @Bean
    public CommandLineRunner initTags(TagsRepository repository) {
        return (args) -> repository.saveAll(SAMPLE_TAGS);
    }

    public static User getSampleUser(int index) {
        return SAMPLE_USERS.get(index);
    }

    public static Word getSampleWord(int index) {
        return SAMPLE_WORDS.get(index);
    }
}
