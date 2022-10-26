package com.mino.springlab.scenario10;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@RequiredArgsConstructor
@Slf4j
public class Receiver {

    @RabbitListener(queues = "#{initialQueue.name}")
    public void onMessageQ1(Message message) {
        System.out.println(" [x] Q1 Received '" + new String(message.getBody()) + "'");
        printHeaders(message.getMessageProperties());

        // message reject
        throw new AmqpRejectAndDontRequeueException("ABCD");
    }

    private void printHeaders(MessageProperties properties) {
        System.out.println("  HEADERS " + properties.getHeaders().size());
        properties.getHeaders().forEach((key, value) -> {
            System.out.println("      " + key + ":" + value);
        });
    }

    @RabbitListener(queues = "#{deadLetterQueue.name}")
    public void onMessageQ2(Message message) {
        System.out.println(" [x] DLQ Received '" + new String(message.getBody()) + "'");
    }

}

