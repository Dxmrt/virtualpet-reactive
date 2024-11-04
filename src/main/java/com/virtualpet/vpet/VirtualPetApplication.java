package com.virtualpet.vpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableR2dbcRepositories
@EnableReactiveMongoRepositories
@EnableScheduling
public class VirtualPetApplication {
    public static void main(String[] args) {
        SpringApplication.run(VirtualPetApplication.class, args);
    }
}