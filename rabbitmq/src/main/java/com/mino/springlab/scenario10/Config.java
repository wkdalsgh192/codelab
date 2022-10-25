package com.mino.springlab.scenario10;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"scenario10"})
public class Config {

    @Bean
    public Queue hello() {
        return QueueBuilder.durable("hello").deadLetterExchange("hello.dlx").build();
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }

    @Bean
    public Binding binding1a(DirectExchange direct, Queue hello) {
        return BindingBuilder.bind(hello).to(direct).with("hello-queue");
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange("hello.dlx");
    }

    @Bean
    Queue deadLetterQueue() {
        return QueueBuilder.durable("hello.dlq").build();
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public Tut10Sender sender() {
        return new Tut10Sender();
    }
}
