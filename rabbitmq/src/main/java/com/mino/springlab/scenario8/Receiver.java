package com.mino.springlab.scenario8;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

@RabbitListener(queues = "hello")
@RequiredArgsConstructor
public class Receiver {

    private final int instance;

    @Autowired
    private ObjectMapper objectMapper;

//    @RabbitHandler
//    public void receive(String in) throws InterruptedException {
//        StopWatch watch = new StopWatch();
//        watch.start();
//        System.out.println("instance " + this.instance + " [x] Received '" + in + "'");
//        System.out.println(in);
//        doWork(in);
//        watch.stop();
//    }

    @RabbitHandler
    public void receive(Employee em) {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + this.instance + " [x] Received '" + em + " jdiowadkla"+"'");
        System.out.println(em);
//        try {
//            Employee emp = objectMapper.readValue(em, Employee.class);
//
//        } catch (JsonProcessingException e) {
//            System.out.println(e.getOriginalMessage());
//        }
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

