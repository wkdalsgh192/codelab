package com.mino.springlab.lecture6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
public class AccountingConsumer {

    @Autowired
    private ObjectMapper objectMapper;
    private final ProcessingErrorHandler processingErrorHandler;

    @RabbitListener(queues = "q.guideline2.accounting.work")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverytag) {
        var messageStr = new String(message.getBody());
        try {
            var emp = objectMapper.readValue(messageStr, Employee.class);

            if (StringUtils.hasText(emp.getName())) {
                log.info(" [x] On accounting: {}", emp);
                channel.basicAck(deliverytag, false);
            }
            else throw new IllegalArgumentException("Name is empty");
        } catch (IllegalArgumentException | IOException e) {
            log.info(" [x] Error Occurred While Consuming Message: {}", messageStr);
            processingErrorHandler.handleErrorMessage(message, channel, deliverytag);
        }
    }

    @RabbitListener(queues = "q.guideline2.accounting.dead")
    public void listenDeadLetter(Message message, Channel channel , @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag)  {
        try {
            var messageStr = new String(message.getBody());
            log.info(" [x] on Accounting Dead Letter at {} for message: {}", LocalDateTime.now(), messageStr);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
