package com.mino.springlab.scenario8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Configuration
@Profile({"scenario8"})
@EnableRabbit
public class Config {

    private static final String EXCHANGE_NAME = "eunbi.exchange";
    private static final String QUEUE_NAME = "eunbi.queue";
    private static final String ROUTING_KEY = "eunbi.routing.#";

    @Bean(name="customTemplate")
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Queue eunbi() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    public Tut8Sender sender() {
        return new Tut8Sender();
    }

    @Bean
    public static Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter jsonConverter = new Jackson2JsonMessageConverter();
        jsonConverter.setClassMapper(classMapper());
        return jsonConverter;
    }
    @Bean
    public static DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("employee", Employee.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }


    private static class ReceiverConfig {

        @Bean
        public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
            final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
            factory.setConnectionFactory(connectionFactory);
            factory.setMessageConverter(jsonMessageConverter());
            factory.setAfterReceivePostProcessors(message -> {
                Optional.of(Employee.class.getName()).ifPresent(t -> message.getMessageProperties().setHeader("__TypeId__", t));
                return message;
            });
            return factory;
        }

    }


//    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

//    @Bean
//    @Primary
//    public ObjectMapper serializingObjectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        JavaTimeModule javaTimeModule = new JavaTimeModule();
//        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer());
//        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());
//        objectMapper.registerModule(javaTimeModule);
//        return objectMapper;
//    }
//
//    public class LocalDateSerializer extends JsonSerializer<LocalDate> {
//
//        @Override
//        public void serialize(LocalDate value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
//            gen.writeString(value.format(FORMATTER));
//        }
//    }
//
//    public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {
//
//        @Override
//        public LocalDate deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
//            return LocalDate.parse(p.getValueAsString(), FORMATTER);
//        }
//    }
}
