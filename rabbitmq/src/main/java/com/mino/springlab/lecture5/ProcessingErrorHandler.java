package com.mino.springlab.lecture5;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
public class ProcessingErrorHandler {

    @NonNull
    private final String deadExchangeName;

    private static final int MAX_RETRY_COUNT = 3;

    public ProcessingErrorHandler(String deadExchangeName) {
        super();
        if (StringUtils.hasText(deadExchangeName)) {
            this.deadExchangeName = deadExchangeName;
        } else {
            throw new IllegalArgumentException("Must define DLX exchange name");
        }
    }

    public String getDeadExchangeName() {
        return deadExchangeName;
    }

    public void handleErrorMessage(Message message, Channel channel, long deliveryTag) {
        var jsonString = new String(message.getBody());
        var rabbitHeader = new RabbitMqHeader(message.getMessageProperties().getHeaders());

        try {
            if (rabbitHeader.getFailedRetryCount() >= MAX_RETRY_COUNT) {
                log.warn(" [x] on Dead Letter at {} on retry {} for message: {}", LocalDateTime.now(), rabbitHeader.getFailedRetryCount(), jsonString);
                channel.basicPublish(getDeadExchangeName(), message.getMessageProperties().getReceivedRoutingKey(), null, message.getBody());
                channel.basicAck(deliveryTag, false);
            } else {
                log.warn(" [x] on Requeue Error at {} on retry {} for message {}", LocalDateTime.now(), rabbitHeader.getFailedRetryCount(), jsonString);
                channel.basicReject(deliveryTag, false);
            }
        } catch (IOException e) {
            log.warn(" [x] on Handling Failure Error at {} on retry {} for message {}", LocalDateTime.now(), rabbitHeader.getFailedRetryCount(), jsonString);
            e.printStackTrace();
        }
    }
}
