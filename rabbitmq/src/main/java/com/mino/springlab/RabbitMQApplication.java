package com.mino.springlab;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class RabbitMQApplication {

//    @Bean
//    @Profile("usage_message")
//    public CommandLineRunner usage() {
//        return args -> {
//            System.out.println("This app uses Spring Profiles to control its behavior. \n");
//            System.out.println("Sample usage: java -jar rabbit-tutorials.jar --spring.profiles.active=hello-world,sender");
//        };
//    }

//    @Bean
//    @Profile("auto_close")
//    public CommandLineRunner tutorial() {
//        return new RabbitAmqpTutorialRunner();
//    }

    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class, args);
    }

}
