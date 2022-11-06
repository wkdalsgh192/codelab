package com.mino.springlab.scenario10;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@Slf4j
public class Receiver {

    @RabbitListener(queues = "#{initialQueue.name}")
    public void onMessageQ1(Message message) throws InterruptedException {
        System.out.println(" [x] Q1 Received '" + new String(message.getBody()) + "'");
        Thread.sleep(10000L);

        // message reject
        throw new AmqpRejectAndDontRequeueException("ABCD");
    }

    @RabbitListener(queues = "#{deadLetterQueue.name}")
    public void onMessageQ2(Message message) {
        System.out.println(" [x] DLQ Received '" + new String(message.getBody()) + "'");
    }

}

