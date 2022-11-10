package com.mino.springlab.scenario4;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

@Profile("scenario4")
public class Tut4Sender {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange direct;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    /**
     * 예상 라우팅 결과
     * black: 인스턴스 1 & 2
     * orange: 인스턴스 1
     * green: 인스턴스 2
     * */
    private final String[] keys = {"orange", "black", "green"};

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    public void send() {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (index.incrementAndGet() == 3) index.set(0);

        String key = keys[index.get()];
        builder.append(key).append(' ');
        builder.append(this.count.getAndIncrement());
        String message = builder.toString();
        template.convertAndSend(direct.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
