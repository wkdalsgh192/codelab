package com.mino.springlab.scenario7;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("scenario7")
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }

    @Bean
    public TopicExchange topic() {return new TopicExchange("tut.topic");}

    @Bean
    public Queue hello() {
        return new Queue("hello", false, true, false);
    }

    @Bean
    public Binding binding1a(FanoutExchange fanout, Queue hello) {
        return BindingBuilder.bind(hello).to(fanout);
    }

    @Bean
    @Profile("sender")
    public Tut7Sender sender() { return new Tut7Sender(); }

    @Bean
    @Profile("receiver1")
    public Receiver receiver1() {
        return new Receiver(1);
    }

    @Bean
    @Profile("receiver2")
    public Receiver receiver2() {
        return new Receiver(2);
    }

    @Bean
    public ConnectionFactory rabbitConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        // .. 이러저런 설정
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("localrabbit");
        connectionFactory.setPassword("localrabbit");
        connectionFactory.setVirtualHost("/");
        // publisher confirm ON
        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }
}
