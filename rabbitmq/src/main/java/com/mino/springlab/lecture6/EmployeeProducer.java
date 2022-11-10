package com.mino.springlab.lecture6;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EmployeeProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private ObjectMapper objectMapper;

    AtomicInteger idx = new AtomicInteger(0);

    @Scheduled(fixedDelay = 5000, initialDelay = 500)
    public void sendMessage() throws JsonProcessingException {
        var employee = new Employee("emp-" + idx.getAndIncrement(), null, LocalDate.now());
        var json = objectMapper.writeValueAsString(employee);
        rabbitTemplate.convertAndSend("x.guideline2.work", "", json);
        log.info(" [x] Sent message: {}", json);
    }

}
