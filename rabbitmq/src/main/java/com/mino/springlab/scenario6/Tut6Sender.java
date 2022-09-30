package com.mino.springlab.scenario6;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

public class Tut6Sender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange direct;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

//    private final String[] keys = {"orange", "black", "green"};

    @PostConstruct
    private void postConstruct() {
        setupCallbacks();
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() throws ExecutionException, InterruptedException, TimeoutException {
        StringBuilder builder = new StringBuilder("Hello to ");
        if (index.incrementAndGet() == 3) index.set(0);

//        String key = keys[index.get()];
        String key = "orange";
        builder.append(key).append(' ');
        builder.append(this.count.get());
        String message = builder.toString();
        CorrelationData correlationData = new CorrelationData("Correlation for message " + message);
        rabbitTemplate.convertAndSend(direct.getName(), key, message, correlationData);
        CorrelationData.Confirm confirm = correlationData.getFuture().get(10, TimeUnit.SECONDS);
        System.out.println("Confirm received, ack = " + confirm.isAck());
        System.out.println(" [x] Sent '" + message + "'");
    }

    private void setupCallbacks() {
        /*
         * Confirms/returns enabled in application.properties - add the callbacks here.
         */
        this.rabbitTemplate.setConfirmCallback((correlation, ack, reason) -> {
            if (correlation != null) {
                System.out.println("Received " + (ack ? " ack " : " nack ") + "for correlation: " + correlation);
            }
        });
        this.rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("Returned: " + returned.getMessage() + "\nreplyCode: " + returned.getReplyCode()
                    + "\nreplyText: " + returned.getReplyText() + "\nexchange/rk: "
                    + returned.getExchange() + "/" + returned.getRoutingKey());
        });
    }
}
