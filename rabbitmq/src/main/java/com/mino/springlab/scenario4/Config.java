package com.mino.springlab.scenario4;

import com.mino.springlab.scenario3.Receiver;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("scenario4")
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("tut.direct");
    }


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
        public Binding binding1a(DirectExchange direct, @Qualifier("queue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(direct).with("orange");
        }

        @Bean
        public Binding binding1b(DirectExchange direct, @Qualifier("queue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(direct).with("black");
        }

        @Bean
        public Binding binding2a(DirectExchange direct, @Qualifier("queue2") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(direct).with("green");
        }

        @Bean
        public Binding binding2b(DirectExchange direct, @Qualifier("queue2") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(direct).with("black");
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }
}

