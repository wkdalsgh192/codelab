package com.mino.springlab.scenario11;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile({"scenario11"})
public class Config {

    @Bean
    public FanoutExchange fanout() {
        return new FanoutExchange("scenario11");
    }

    @Bean
    public Tut11Sender sender() {
        return new Tut11Sender();
    }

    @Bean(name = {"queue1"})
    public Queue autoDeleteQueue1() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-expires", 30000);
        return new Queue("hello", true, false, true, args);
    }

    @Bean(name = {"queue2"})
    public Queue autoDeleteQueue2() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-expires", 30000);
        return new Queue("bye",true, false, true, args);
    }

    @Bean
    public Binding binding1(FanoutExchange fanout, @Qualifier("queue1") Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    @Bean
    public Binding binding2(FanoutExchange fanout, @Qualifier("queue2") Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(fanout);
    }

    private static class ReceiverConfig {

        @Bean
        public Receiver receiver1() {
            return new Receiver(1);
        }

        @Bean
        public Receiver receiver2() {
            return new Receiver(2);
        }

        @Bean
        public RabbitListenerContainerFactory<SimpleMessageListenerContainer> prefetchOneContainerFactory(
                SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory)
        {
            var factory = new SimpleRabbitListenerContainerFactory();
            configurer.configure(factory, connectionFactory);
            return factory;
        }
    }
}
