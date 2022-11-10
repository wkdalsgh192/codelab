package com.mino.springlab.scenario10;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

public class Tut10Sender {

//    @Autowired
//    private RabbitTemplate template;
//
//    @Autowired
//    private DirectExchange direct;
//
//    AtomicInteger dots = new AtomicInteger(0);
//
//    AtomicInteger count = new AtomicInteger(0);
//
//    @Scheduled(fixedDelay = 5000, initialDelay = 500)
//    public void send() {
//        StringBuilder builder = new StringBuilder("Hello");
//        if (dots.incrementAndGet() == 4) {
//            dots.set(1);
//        }
//
//        for (int i = 0; i < dots.get(); i++) {
//            builder.append('.');
//        }
//
//        builder.append(count.incrementAndGet());
//        String message = builder.toString();
//        template.convertAndSend(direct.getName(), "hello-queue", message);
//        System.out.println(" [x] Sent '" + message + " to" + direct.getName() +"'");
//    }

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private FanoutExchange initialExchange;

    AtomicInteger dots = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedDelay = 10000, initialDelay = 500)
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
        template.convertAndSend(initialExchange.getName(), "", message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
