package com.mino.springlab.lecture7;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class SpringPictureProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final List<String> SOURCES = List.of("mobile", "web");

    private final List<String> TYPES = List.of("jpg", "png", "svg");

    private Integer num = 0;

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    public void sendMessage() throws JsonProcessingException {
        var picture = new Picture();
        picture.setName("Picture: " + num++);
        picture.setSize(ThreadLocalRandom.current().nextLong(7500, 10000));
        picture.setSource(SOURCES.get(num % SOURCES.size()));
        picture.setType(TYPES.get(num % TYPES.size()));
        var json = objectMapper.writeValueAsString(picture);
        rabbitTemplate.convertAndSend("x.spring.work",picture.getType(), json);
        log.info("[x] Sent - " + json + " via " + picture.getType());
    }
}
