package com.mino.springlab.scenario7;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver1 {

    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive(String in) throws InterruptedException {
        System.out.println("Instance 1 [x] Received '" + in + "'");
        doWork(in);
        Thread.sleep(2000);
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
