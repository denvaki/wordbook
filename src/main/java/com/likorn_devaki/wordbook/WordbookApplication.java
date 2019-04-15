package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
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

    private List<WordRecord> sampleWordRecords = Arrays.asList(
            new WordRecord(0, "kaks", "two"),
            new WordRecord(0, "kolm", "three"),
            new WordRecord(0, "neli", "four"),
            new WordRecord(1, "tere", "hello"),
            new WordRecord(1, "head aega", "goodbye"));

    public static void main(String[] args) {
        SpringApplication.run(WordbookApplication.class, args);
    }

    @Bean
    public CommandLineRunner initWords(WordsRepo repository) {
        String created = LocalDateTime.now().toString();
        sampleWordRecords.forEach(w -> w.setCreated(created));
        return (args) -> repository.saveAll(sampleWordRecords);
    }

    @Bean
    public CommandLineRunner initUsers(UsersRepo repository) {
        return (args) -> {
            String created = LocalDateTime.now().toString();
            sampleUsers.forEach(u -> u.setCreated(created));
            repository.saveAll(sampleUsers);
        };
    }

    public static User getSampleUserWithNullId(int index) {
        User sampleUser = sampleUsers.get(index);
        sampleUser.setId(null);
        return sampleUser;
    }
}
