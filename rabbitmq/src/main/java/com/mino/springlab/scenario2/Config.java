package com.mino.springlab.scenario2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile({"scenario2"})
public class Config {

    @Bean
    public Queue hello() {
        return new Queue("hello");
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
            factory.setPrefetchCount(1);

            return factory;
        }
    }

    @Bean
    public Tut2Sender sender() {
        return new Tut2Sender();
    }
}
