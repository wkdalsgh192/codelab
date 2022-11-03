package com.mino.springlab.lecture3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class PictureProducer {

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
        picture.setSize(ThreadLocalRandom.current().nextLong(1, 10000));
        picture.setSource(SOURCES.get(num % SOURCES.size()));
        picture.setType(TYPES.get(num % TYPES.size()));
        var json = objectMapper.writeValueAsString(picture);

        var sb = new StringBuilder();
        // 1st word is picture source
        sb.append(picture.getSource());
        sb.append(".");

        // 2nd word is based on picture size
        if (picture.getSize() > 4000) {
            sb.append("large");
        } else {
            sb.append("small");
        }
        sb.append(".");

        // 3rd word is picture type
        sb.append(picture.getType());

        var routingKey = sb.toString();
        rabbitTemplate.convertAndSend("x.picture2",routingKey, json);
        log.info("[x] Sent - " + json + " via " + routingKey);
    }

}
