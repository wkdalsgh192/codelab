package com.mino.springlab.lecture3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture3")
@Configuration
public class RabbitMQConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonMapper.builder().findAndAddModules().build();
    }

    @Bean
    public TopicExchange topicExchange() {return new TopicExchange("x.picture2"); }

    @Bean
    public Queue imageQueue() {
        return new Queue("q.picture.image");
    }

    @Bean
    public Queue vectorQueue() {
        return new Queue("q.picture.vector");
    }

    @Bean
    public Queue logQueue() {
        return new Queue("q.picture.log");
    }

    @Bean
    public Queue filterQueue() {
        return new Queue("q.picture.filter");
    }

    @Bean
    public Binding binding1(Queue imageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(imageQueue).to(topicExchange).with("*.*.png");
    }

    @Bean
    public Binding binding2(Queue imageQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(imageQueue).to(topicExchange).with("#.jpg");
    }

    @Bean
    public Binding binding3(Queue vectorQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(vectorQueue).to(topicExchange).with("#.jpg");
    }

    @Bean
    public Binding binding4(Queue filterQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(filterQueue).to(topicExchange).with("mobile.#");
    }

    @Bean
    public Binding binding5(Queue logQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(logQueue).to(topicExchange).with("*.large.svg");
    }

    @Bean
    public PictureProducer producer() {
        return new PictureProducer();
    }

    @Bean
    public PictureConsumer consumer() {
        return new PictureConsumer();
    }
}
