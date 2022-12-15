package com.mino.springlab.lecture8;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class InvoiceProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String EXCHANGE = "x.invoice";

    @Scheduled(fixedDelay = 20000, initialDelay = 500)
    public void sendInvoiceCreated() {
        var randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(100, 200);
        var invoiceCreatedMessage = new InvoiceCreatedMessage(152.26, LocalDate.now().minusDays(2), "USD", randomInvoiceNumber);
        rabbitTemplate.convertAndSend(EXCHANGE, invoiceCreatedMessage.getInvoiceNumber(), invoiceCreatedMessage);
    }

    @Scheduled(fixedDelay = 20000, initialDelay = 500)
    public void sendInvoicePaid() {
        var randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(200, 300);
        var randomPaymentNumber = "PAY-" + ThreadLocalRandom.current().nextInt(800, 1000);
        var invoicePaidMessage = new InvoicePaidMessage(randomInvoiceNumber, LocalDate.now(), randomPaymentNumber);
        rabbitTemplate.convertAndSend(EXCHANGE, invoicePaidMessage.getInvoiceNumber(), invoicePaidMessage);
    }

    @Scheduled(fixedDelay = 20000, initialDelay = 500)
    public void sendInvoiceCancelled() {
        var randomInvoiceNumber = "INV-" + ThreadLocalRandom.current().nextInt(300, 400);
        var invoiceCancelledMessage = new InvoiceCancelledMessage(LocalDate.now(), randomInvoiceNumber, "Just a test");
        rabbitTemplate.convertAndSend(EXCHANGE, invoiceCancelledMessage.getInvoiceNumber(), invoiceCancelledMessage);
    }
}
