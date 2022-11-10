package com.mino.springlab.scenario4;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Receiver {

    @RabbitListener(queues = "#{queue1.name}")
    public void receive1(String in, int i) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{queue2.name}")
    public void receive2(String in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(String in, int receiver) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }

}
