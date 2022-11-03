package com.mino.springlab.scenario8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

public class Tut8Sender {

    @Autowired
    @Qualifier("customTemplate")
    private RabbitTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DirectExchange direct;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        // 단순 스트링을 보내는 경우
//        String message = "Hello";
//        template.convertAndSend(queue.getName(), message);
//        System.out.println(" [x] Sent '" + message + "'");

        // Object 객체를 보내는 경우 -- JSON으로 변환해서 보낸다
        try {
            String json = objectMapper.writeValueAsString(new Employee("minho", "test"));
            template.convertAndSend(direct.getName(), "eunbi.routing.#", new Employee("minho", "test"));
            System.out.println(" [x] Sent '" + json + "'");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 어떤 메시지를 받지 않는 경우
    }
}
