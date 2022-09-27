package com.mino.springlab.scenario1;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@RabbitListener(queues = "queue1")
public class Receiver {

    @RabbitHandler
    public void receive(String in) {
        System.out.println("  [x] Received '" + in + "'");
    }
}
