package com.mino.springlab.scenario8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.impl.AMQImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Tut8Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("queue2")
    private Queue queue;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    public void send() {
        new AMQP.BasicProperties().builder().type("");
        // 단순 스트링을 보내는 경우
        String message = "Hello";
        template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");

        // Object 객체를 보내는 경우 -- JSON으로 변환해서 보낸다
        try {
            String json = objectMapper.writeValueAsString(new Employee("minho", "test"));
            template.convertAndSend(queue.getName(), new Employee("minho", "test"), msg -> {
                msg.getMessageProperties().setType("employee");
                return msg;
            });
            System.out.println(" [x] Sent '" + json + "'");

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 어떤 메시지를 받지 않는 경우
    }
}
