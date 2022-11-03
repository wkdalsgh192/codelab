package com.mino.springlab.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringStartApplication {

    @Autowired
    MyService myService;

    public static void main(String[] args) {
        SpringApplication.run(SpringStartApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner() {
        return (this::run);
    }

    private void run(ApplicationArguments args) {
        myService.doSomething();
    }
}
