package com.mino.springlab.lecture5;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class PictureImageConsumer {

    private static final String DEAD_EXCHANGE_NAME = "x.guideline.dead";

    @Autowired
    private ObjectMapper objectMapper;

    private final ProcessingErrorHandler processingErrorHandler = new ProcessingErrorHandler(DEAD_EXCHANGE_NAME);


    @RabbitListener(queues = "q.guideline.image.work")
    public void listen(Message message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        var jsonString = new String(message.getBody());
        try {
            var picture = objectMapper.readValue(jsonString, Picture.class);

            if (picture.getSize() > 9000) {
                // NOTE: If an error occurs, RabbitMQ basically tries to requeue to complete the task, which causes an infinite loop of the error
//            throw new IllegalArgumentException();
                // NOTE Don't requeue and discard message Exception
                throw new IOException();
            } else {
                log.info(" [x] On picture: {}", picture);
                channel.basicAck(deliveryTag, false);
            }
        } catch (IOException e) {
            log.error(" [x] On Error: {}", jsonString + ": " + e.getMessage());
            processingErrorHandler.handleErrorMessage(message, channel, deliveryTag);
        }
    }
}
