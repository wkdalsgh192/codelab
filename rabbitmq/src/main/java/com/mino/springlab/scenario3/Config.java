package com.mino.springlab.scenario3;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("scenario3")
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("tut.fanout");
    }

    private static class ReceiverConfig {

        @Bean(name={"queue1"})
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue(new MinhoNamingStrategy("minhoJang-"));
//            return new AnonymousQueue();
        }

        @Bean(name={"queue2"})
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1(FanoutExchange fanout, @Qualifier("queue1") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(fanout);
        }

        @Bean
        public Binding binding2(FanoutExchange fanout, @Qualifier("queue2") Queue autoDeleteQueue) {
            return BindingBuilder.bind(autoDeleteQueue).to(fanout);
        }

        @Bean
        public Receiver receiver() {
            return new Receiver();
        }
    }
}
