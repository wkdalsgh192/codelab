package com.mino.springlab.lecture6;

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
        if (StringUtils.hasText(deadExchangeName)) this.deadExchangeName = deadExchangeName;
        else throw new IllegalArgumentException("DLX Name Must Be Defined");
    }

    public void handleErrorMessage(Message message, Channel channel, long deliveryTag) {
        var messageStr = new String(message.getBody());
        var rabbitHeader = new RabbitMQHeader(message.getMessageProperties().getHeaders());

        try {
            if (rabbitHeader.getFailedRetryCount() >= MAX_RETRY_COUNT) {
                log.warn(" [x] Routed To DLX at {} on retry {} for message: {}", LocalDateTime.now(), rabbitHeader.getFailedRetryCount(), messageStr);
                channel.basicPublish(deadExchangeName, message.getMessageProperties().getReceivedRoutingKey(), null, message.getBody());
                channel.basicAck(deliveryTag, false);
            } else {
                log.warn(" [x] Message Enqueued at {} on retry {} for message {}", LocalDateTime.now(), rabbitHeader.getFailedRetryCount(), messageStr);
                channel.basicReject(deliveryTag, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
