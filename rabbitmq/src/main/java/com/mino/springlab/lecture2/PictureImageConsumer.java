package com.mino.springlab.lecture2;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class PictureImageConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "q.picture.image")
    public void listen(String message) throws JsonProcessingException {
        var picture = objectMapper.readValue(message, Picture.class);
        log.info("On image: {}", picture);
    }
}
