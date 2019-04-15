package com.likorn_devaki.wordbook;

import com.likorn_devaki.wordbook.Entities.User;
import com.likorn_devaki.wordbook.Entities.WordRecord;
import com.likorn_devaki.wordbook.Repos.UsersRepo;
import com.likorn_devaki.wordbook.Repos.WordsRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WordbookApplication {

    private List<User> sampleUsers = Arrays.asList(
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
        return (args) -> repository.saveAll(sampleWordRecords);
    }

    @Bean
    public CommandLineRunner initUsers(UsersRepo repository) {
        return (args) -> repository.saveAll(sampleUsers);
    }

    User getSampleUser(int index) {
        return sampleUsers.get(index);
    }
}
