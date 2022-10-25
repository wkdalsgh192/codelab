package com.mino.springlab.scenario10;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


@RequiredArgsConstructor
@Slf4j
public class Receiver {
    @RabbitListener(queues = "hello")
    public void receive(Message message) throws ErrorHandlingException {
        throw new ErrorHandlingException("비즈니스 로직 예외 발생");
    }

    @RabbitListener(queues = "dlq")
    public void processFailedMessages(Message message) {
        log.info("Received failed message: {}", message.toString());
    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}

