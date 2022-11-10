package com.mino.springlab.lecture7;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class SpringPictureConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "q.spring.image.work")
    public void listenImage(String message) throws IOException {
        var picture = objectMapper.readValue(message, Picture.class);
        log.info("Consuming image {}", picture.getName());

        if (picture.getSize() > 9000) {
            throw new IllegalArgumentException("Image is too large : " + picture.getName());
        }

        log.info("Business Logic Inserted Here for : " + picture.getName());
    }

    @RabbitListener(queues = "q.spring.vector.work")
    public void listenVector(String message) throws IOException {
        var picture = objectMapper.readValue(message, Picture.class);
        log.info("Consuming vector {}", picture.getName());
        log.info("Business Logic Inserted Here for : " + picture.getName());
    }
}
