package com.mino.springlab.scenario1;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

@Profile("scenario1")
public class Tut1Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("queue1")
    private Queue queue;

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        String message = "Hello, World";
        this.template.convertAndSend(queue.getName(), message);
        System.out.println("  [x] Sent '" + message + "'");
    }
}
