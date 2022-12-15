package com.mino.springlab.lecture8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Slf4j
@RabbitListener(queues = "q.invoice.two")
public class InvoiceConsumer {

    @RabbitHandler
    public void handleInvoiceCreated(InvoiceCreatedMessage message) {
        log.info("Invoice created: {}", message);
    }

    @RabbitHandler
    public void handleInvoicePaid(InvoicePaidMessage message) {
        log.info("Invoice paid: {}", message);
    }

    @RabbitHandler(isDefault = true)
    public void listenDefault(Message message) {
        log.info("Default invoice listener : {}", message);
    }
}
