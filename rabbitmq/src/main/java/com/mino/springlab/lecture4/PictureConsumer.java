package com.mino.springlab.lecture4;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PictureConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = {"q.picture.image", "q.picture.vector", "q.picture.log", "q.picture.filter"})
    public void listen(Message messageAmqp) throws JsonProcessingException {
        var jsonString = new String(messageAmqp.getBody());
        var picture = objectMapper.readValue(jsonString, Picture.class);

        if (picture.getSize() > 7000) {
            // NOTE: If an error occurs, RabbitMQ basically tries to requeue to complete the task, which causes an infinite loop of the error
//            throw new IllegalArgumentException();
            // NOTE Don't requeue and discard message Exception
            throw new AmqpRejectAndDontRequeueException("Picture size is too large");
        }
        log.info(" [x] On picture: {} with routing key: {}", picture, messageAmqp.getMessageProperties().getReceivedRoutingKey());
    }

    @RabbitListener(queues = "#{deadLetterQueue.name}")
    public void listenDeadLetterMessage(Message message) throws JsonProcessingException {
        var jsonString = new String(message.getBody());
        var picture = objectMapper.readValue(jsonString, Picture.class);
        log.info(" [x] On Dead Letter: {} with routing key: {}", picture, message.getMessageProperties().getReceivedRoutingKey());
    }
}
