package com.mino.springlab.lecture3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
        log.info("On picture: {} with routing key: {}", picture, messageAmqp.getMessageProperties().getReceivedRoutingKey());
    }
}
