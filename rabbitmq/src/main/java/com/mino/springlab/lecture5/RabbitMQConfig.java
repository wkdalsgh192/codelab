package com.mino.springlab.lecture5;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("lecture5")
@Configuration
public class RabbitMQConfig {

    private static final String DEAD_LETTER_EXCHANGE = "dlx";
    private static final String DEAD_LETTER_QUEUE = "dlq";
    private static final String IMAGE_PROCESSOR_QUEUE_NAME = "q.picture.image";
    private static final String VECTOR_PROCESSOR_QUEUE_NAME = "q.picture.vector";
    private static final String FILTER_PROCESSOR_QUEUE_NAME = "q.picture.filter";
    private static final String LOG_PROCESSOR_QUEUE_NAME = "q.picture.log";


//    @Bean
//    public ObjectMapper objectMapper() {
//        return JsonMapper.builder().findAndAddModules().build();
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {return new TopicExchange("x.picture2"); }
//
//    @Bean
//    public FanoutExchange deadLetterExchange() {
//        return new FanoutExchange(DEAD_LETTER_EXCHANGE);
//    }
//
//    @Bean
//    public Queue deadLetterQueue() { return QueueBuilder.nonDurable(DEAD_LETTER_QUEUE).build();}
//
//    @Bean
//    public Binding bindingDeadLetterQueueAndExchange(FanoutExchange deadLetterExchange, Queue deadLetterQueue) {
//        return BindingBuilder.bind(deadLetterQueue).to(deadLetterExchange);
//    }
//
//    @Bean
//    public Queue imageQueue() {
//        return QueueBuilder.nonDurable(IMAGE_PROCESSOR_QUEUE_NAME).deadLetterExchange(DEAD_LETTER_EXCHANGE).build();
//    }
//
//    @Bean
//    public Queue vectorQueue() {
//        return QueueBuilder.nonDurable(VECTOR_PROCESSOR_QUEUE_NAME).deadLetterExchange(DEAD_LETTER_EXCHANGE).build();
//    }
//
//    @Bean
//    public Queue logQueue() {
//        return QueueBuilder.nonDurable(FILTER_PROCESSOR_QUEUE_NAME).deadLetterExchange(DEAD_LETTER_EXCHANGE).build();
//    }
//
//    @Bean
//    public Queue filterQueue() {
//        return QueueBuilder.nonDurable(LOG_PROCESSOR_QUEUE_NAME).deadLetterExchange(DEAD_LETTER_EXCHANGE).build();
//    }
//
//    @Bean
//    public Binding binding1(Queue imageQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(imageQueue).to(topicExchange).with("*.*.png");
//    }
//
//    @Bean
//    public Binding binding2(Queue imageQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(imageQueue).to(topicExchange).with("#.jpg");
//    }
//
//    @Bean
//    public Binding binding3(Queue vectorQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(vectorQueue).to(topicExchange).with("#.jpg");
//    }
//
//    @Bean
//    public Binding binding4(Queue filterQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(filterQueue).to(topicExchange).with("mobile.#");
//    }
//
//    @Bean
//    public Binding binding5(Queue logQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(logQueue).to(topicExchange).with("*.large.svg");
//    }

    @Bean
    public PictureProducer producer() {
        return new PictureProducer();
    }

    @Bean
    public PictureImageConsumer imageConsumer() {
        return new PictureImageConsumer();
    }

    @Bean
    public PictureVectorConsumer vectorConsumer() { return new PictureVectorConsumer(); }
}
