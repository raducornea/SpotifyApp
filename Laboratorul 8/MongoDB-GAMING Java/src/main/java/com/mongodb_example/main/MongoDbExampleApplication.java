package com.mongodb_example.main;

import com.mongodb_example.main.repositories.SteamUserRepository;
import com.mongodb_example.main.steam_users.models.Game;
import com.mongodb_example.main.steam_users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@SpringBootApplication
@EnableMongoRepositories
//public class MongoDbExampleApplication implements CommandLineRunner {
public class MongoDbExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongoDbExampleApplication.class, args);
    }


//    @Override
//    public void run(String... args) throws Exception {
//        createAUser();
//        System.out.println("All users");
//
//        showAllUsers();
//        removeUser(getIdByUsername("Ratonu"));
//        System.out.println("Users after removal");
//
//        showAllUsers();
//        createAUser();
//        updateUser("Ratonu");
//        System.out.println("Users after add and update");
//        showAllUsers();
//    }

}
