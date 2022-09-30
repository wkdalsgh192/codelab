package com.mino.springlab.scenario6;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Receiver {

    @RabbitListener(queues = "#{autoDeleteQueue1}")
    public void receive(String in) throws InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(" [x] Received '" + in + "'");
        doWork(in);
        watch.stop();
        System.out.println(" [x] Done in " +
                watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
