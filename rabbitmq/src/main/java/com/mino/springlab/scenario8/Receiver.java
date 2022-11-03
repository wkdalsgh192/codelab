package com.mino.springlab.scenario8;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

@RequiredArgsConstructor
public class Receiver {
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

//    @RabbitListener(queues = "eunbi.queue", containerFactory = "simpleRabbitListenerContainerFactory")
    public void receive(Employee employee) {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println(" [x] Received '" + employee + " jdiowadkla"+"'");
        System.out.println(employee);
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

