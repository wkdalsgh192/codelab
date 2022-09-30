package com.mino.springlab.scenario6;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("scenario6")
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
    public Queue autoDeleteQueue1() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding1a(DirectExchange direct, Queue autoDeleteQueue1) {
        return BindingBuilder.bind(autoDeleteQueue1).to(direct).with("orange");
    }

    @Bean
    public Tut6Sender sender() { return new Tut6Sender(); }

    @Bean
    public Receiver receiver() {
        return new Receiver();
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
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
        return connectionFactory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory rabbitConnectionFactory) {
        RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
//        template.setConfirmCallback((correlation, ack, reason) -> {
//            if (correlation != null) {
//                System.out.println("Received " + (ack ? " ack " : " nack ") + "for correlation: " + correlation);
//            }
//        });
//
//        template.setReturnsCallback(returned -> {
//            System.out.println("Returned: " + returned.getMessage() + "\nreplyCode: " + returned.getReplyCode()
//                    + "\nreplyText: " + returned.getReplyText() + "\nexchange/rk: "
//                    + returned.getExchange() + "/" + returned.getRoutingKey());
//        });
        return template;
    }
}
