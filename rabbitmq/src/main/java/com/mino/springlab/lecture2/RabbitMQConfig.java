package com.mino.springlab.lecture2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture2")
@Configuration
public class RabbitMQConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }

    @Bean
    public DirectExchange directExchange() {return new DirectExchange("x.picture"); }

    @Bean
    public Queue imageQueue() {
        return new Queue("q.picture.image");
    }

    @Bean
    public Queue vectorQueue() {
        return new Queue("q.picture.vector");
    }

    @Bean
    public Binding binding1(Queue imageQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(imageQueue).to(directExchange).with("jpg");
    }

    @Bean
    public Binding binding2(Queue imageQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(imageQueue).to(directExchange).with("png");
    }

    @Bean
    public Binding binding3(Queue vectorQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(vectorQueue).to(directExchange).with("svg");
    }

    @Bean
    public PictureProducer producer() {
        return new PictureProducer();
    }

    @Bean
    public PictureImageConsumer imageConsumer() {return new PictureImageConsumer();}

    @Bean
    public PictureVectorConsumer vectorConsumer() {return new PictureVectorConsumer();}
}
