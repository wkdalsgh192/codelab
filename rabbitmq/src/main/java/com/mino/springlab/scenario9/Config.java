package com.mino.springlab.scenario9;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("scenario9")
@Configuration
public class Config {

    @Profile("client")
    private static class ClientConfig {

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Client client() {
            return new Client();
        }

    }

    @Profile("server")
    private static class ServerConfig {

        @Bean
        public Queue autoDeleteQueue() { return new AnonymousQueue(); }

        @Bean
        public Queue rpc() {
            return new Queue("tut.rpc.requests");
        }

        @Bean
        public DirectExchange exchange() {
            return new DirectExchange("tut.rpc");
        }

        @Bean
        public Binding binding(DirectExchange exchange,
                               Queue rpc) {
            return BindingBuilder.bind(rpc)
                    .to(exchange)
                    .with("rpc");
        }

        @Bean
        public Server server() {
            return new Server();
        }

    }
}