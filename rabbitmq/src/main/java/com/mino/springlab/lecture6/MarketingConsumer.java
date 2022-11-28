package com.mino.springlab.lecture6;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

@Slf4j
public class MarketingConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "q.guideline2.marketing.work")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverytag)
    throws IOException {
        var messageStr = new String(message.getBody());
        var emp = objectMapper.readValue(messageStr, Employee.class);
        log.info(" [x] On marketing: {}", emp);
        channel.basicAck(deliverytag, false);
    }
}
