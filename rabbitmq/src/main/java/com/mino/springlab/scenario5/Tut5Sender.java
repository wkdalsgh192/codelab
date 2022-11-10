package com.mino.springlab.scenario5;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Profile("scenario5")
public class Tut5Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private TopicExchange topic;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    /**
     * 예상 라우팅 결과
     * quick.orange.rabbit: 인스턴스 1
     * lazy.orange.elephant: 인스턴스 1 & 2
     * quick.orange.fox: 인스턴스 1
     * lazy.brown.fox: 인스턴스 2
     * lazy.pink.rabbit: 인스턴스 1 & 2
     * quick.brown.fox: none
     * */

    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (index.incrementAndGet() == 6) index.set(0);

        String key = keys[index.get()];
        builder.append(key).append(' ');
        builder.append(this.count.getAndIncrement());
        String message = builder.toString();
        template.convertAndSend(topic.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
