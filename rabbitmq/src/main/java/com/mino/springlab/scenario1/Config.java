package com.mino.springlab.scenario1;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile({"scenario1"})
@Configuration
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
    }

    @Bean
//    @Profile("receiver")
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
//    @Profile("sender")
    public Sender sender() {
        return new Sender();
    }
}
