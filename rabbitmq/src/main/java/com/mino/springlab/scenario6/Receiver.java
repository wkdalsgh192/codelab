package com.mino.springlab.scenario6;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1}")
    public void receive(String in) throws InterruptedException {
        System.out.println(" [x] Received '" + in + "'");
        doWork(in);
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
