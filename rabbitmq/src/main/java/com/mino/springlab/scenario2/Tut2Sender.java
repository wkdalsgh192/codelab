package com.mino.springlab.scenario2;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Profile("scenario2")
public class Tut2Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    @Qualifier("queue2")
    private Queue queue;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

//    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello");
        if (dots.incrementAndGet() == 4) {
            dots.set(1);
        }

        for (int i = 0; i < dots.get(); i++) {
            builder.append('.');
        }

        builder.append(count.incrementAndGet());
        String message = builder.toString();
        template.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
