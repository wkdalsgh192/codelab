package com.mino.springlab.scenario7;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut7Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private FanoutExchange fanout;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 500, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (index.incrementAndGet() == 3) index.set(0);

        String key = "orange";
        builder.append(key).append(' ');
        builder.append(this.count.get());
        String message = builder.toString();
        rabbitTemplate.convertAndSend(fanout.getName(), "", message);
        System.out.println(" [x] Sent '" + message + "'");

    }
}
