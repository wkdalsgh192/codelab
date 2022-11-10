package com.mino.springlab.scenario5;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class Receiver {

    @RabbitListener(queues = "#{queue1.name}")
    public void receive1(String in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{queue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws InterruptedException {
        System.out.println("instance " + receiver + " [x] Received '" + in + "'");
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
