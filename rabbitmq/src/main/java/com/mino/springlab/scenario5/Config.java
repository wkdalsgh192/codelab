package com.mino.springlab.scenario5;

import com.mino.springlab.scenario3.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("scenario5")
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


    private static class ReceiverConfig {

        @Bean(name = {"queue1"})
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean(name = {"queue2"})
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(TopicExchange topic, @Qualifier("queue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(topic).with("*.orange.*");
        }

        @Bean
        public Binding binding1b(TopicExchange topic, @Qualifier("queue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(topic).with("*.*.rabbit");
        }

        @Bean
        public Binding binding2a(TopicExchange topic, @Qualifier("queue2") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(topic).with("lazy.#");
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }

    @Bean
    public Tut5Sender sender() {
        return new Tut5Sender();
    }
}

