package com.mino.springlab.scenario7;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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

    @Profile("receiver1")
    private static class ReceiverConfig1 {
        @Bean
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(FanoutExchange fanout, Queue autoDeleteQueue1) {
            return BindingBuilder.bind(autoDeleteQueue1).to(fanout);
        }
        @Bean
        public Receiver1 receiver1() {
            return new Receiver1();
        }
    }

    @Profile("receiver2")
    private static class ReceiverConfig2 {
        @Bean
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding2(FanoutExchange fanout, Queue autoDeleteQueue2) {
            return BindingBuilder.bind(autoDeleteQueue2).to(fanout);
        }

        @Bean
        public Receiver2 receiver2() {
            return new Receiver2();
        }
    }


    @Bean
    @Profile("sender")
    public Tut7Sender sender() { return new Tut7Sender(); }

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
