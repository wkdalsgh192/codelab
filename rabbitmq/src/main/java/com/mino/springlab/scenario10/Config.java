package com.mino.springlab.scenario10;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile({"scenario10"})
public class Config {

    private final String DEAD_LETTER_EXCHANGE = "dlx";
    private final String DEAD_LETTER_QUEUE = "dlq";

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public Tut10Sender sender() {
        return new Tut10Sender();
    }

    @Bean
    public FanoutExchange initialExchange() {
        return new FanoutExchange("x1");
    }

    @Bean
    public FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue initialQueue() {
        return QueueBuilder.nonDurable("q1").deadLetterExchange(DEAD_LETTER_EXCHANGE).build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.nonDurable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding bindingDeadLetterQueueAndExchange(FanoutExchange deadLetterExchange, Queue deadLetterQueue) {
        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
    }

    @Bean
    public Binding bindingX1Q1(FanoutExchange initialExchange, Queue initialQueue) {
        return BindingBuilder.bind(initialQueue).to(initialExchange);
    }
}
