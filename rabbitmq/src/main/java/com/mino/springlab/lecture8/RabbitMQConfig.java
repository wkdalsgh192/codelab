package com.mino.springlab.lecture8;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Profile("lecture8")
@Configuration
public class RabbitMQConfig {

//    @Bean(name = "rabbitListenerContainerFactory")
//    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
//            SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        configurer.configure(factory, connectionFactory);
//
//        factory.setAfterReceivePostProcessors(message -> {
//            var type = message.getMessageProperties().getHeaders().get("type").toString();
//            String typeId = null;
//
//            if (StringUtils.startsWithIgnoreCase(type, "invoice.paid")) {
//                typeId = InvoicePaidMessage.class.getName();
//            } else if (StringUtils.startsWithIgnoreCase(type, "invoice.created")) {
//                typeId = InvoiceCreatedMessage.class.getName();
//            } else if (StringUtils.startsWithIgnoreCase(type, "invoice.cancelled")) {
//                typeId = Picture.class.getName();
//            }
//
//            Optional.ofNullable(typeId).ifPresent(t -> message.getMessageProperties().setHeader("__TypeId__", t));
//
//            return message;
//        });
//
//        return factory;
//    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ObjectMapper jsonObjectMapper) {
        return new Jackson2JsonMessageConverter(jsonObjectMapper);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter jsonMessageConverter) {
        final var jsonRabbitTemplate = new RabbitTemplate(connectionFactory);
        jsonRabbitTemplate.setMessageConverter(jsonMessageConverter);
        return jsonRabbitTemplate;
    }

    @Bean
    public CustomExchange fanoutExchange() {return new CustomExchange("x.invoice", "x-consistent-hash"); }

    @Bean
    public Queue invoiceQueue() {
        return new Queue("q.invoice", false, false, false);
    }

//    @Bean
//    public Queue invoiceQueueOne() {
//        return new Queue("q.invoice", false, false, false);
//    }
//
//    @Bean
//    public Queue invoiceQueueTwo() {
//        return new Queue("q.invoice", false, false, false);
//    }
//
//    @Bean
//    public Binding invoiceBinding(FanoutExchange fanoutExchange, Queue invoiceQueue) {
//        return BindingBuilder.bind(invoiceQueue).to(fanoutExchange);
//    }

    @Bean
    public InvoiceProducer producer() {
        return new InvoiceProducer();
    }

    @Bean
    public InvoiceConsumer consumer() {
        return new InvoiceConsumer();
    }
}
