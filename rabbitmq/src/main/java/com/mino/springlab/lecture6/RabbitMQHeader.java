package com.mino.springlab.lecture6;

import java.util.*;

public class RabbitMQHeader {

    private static final String WAIT_QUEUE_KEYWORD = "wait";
    private List<RabbitMQHeaderXDeath> xDeathList = new ArrayList<>(2);

    private String xDeathExchange;
    private String xDeathQueue;
    private String xDeathReason;
    public RabbitMQHeader(Map<String, Object> headers) {
        if (Objects.nonNull(headers)) {
            Optional.ofNullable(headers.get("x-first-death-exchange")).ifPresent(x -> this.xDeathExchange = x.toString());
            Optional.ofNullable(headers.get("x-first-death-queue")).ifPresent(x -> this.xDeathQueue = x.toString());
            Optional.ofNullable(headers.get("x-first-death-reason")).ifPresent(x -> this.xDeathReason = x.toString());

            var xDeathHeaders = (List<Map<String, Object>>) headers.get("x-death");

            if (Objects.nonNull(xDeathHeaders)) {
                for (Map<String, Object> xDeathHeader : xDeathHeaders) {
                    RabbitMQHeaderXDeath rhx = new RabbitMQHeaderXDeath();
                    Optional.ofNullable(xDeathHeader.get("count")).ifPresent(s -> rhx.setCount(Integer.parseInt(s.toString())));
                    Optional.ofNullable(xDeathHeader.get("reason")).ifPresent(s -> rhx.setReason(s.toString()));
                    Optional.ofNullable(xDeathHeader.get("exchange")).ifPresent(s -> rhx.setExchange(s.toString()));
                    Optional.ofNullable(xDeathHeader.get("queue")).ifPresent(s -> rhx.setQueue(s.toString()));
                    Optional.ofNullable(xDeathHeader.get("routingKeys")).ifPresent(s -> rhx.setRoutingKeys((List<String>) s));
                    Optional.ofNullable(xDeathHeader.get("time")).ifPresent(s -> rhx.setTime((Date) s));

                    xDeathList.add(rhx);
                }
            }

        }
    }

    public int getFailedRetryCount() {
        for (var xDeath : xDeathList) {
            if (xDeath.getExchange().toLowerCase().endsWith(WAIT_QUEUE_KEYWORD) && xDeath.getQueue().toLowerCase().endsWith(WAIT_QUEUE_KEYWORD))
                return xDeath.getCount();
        }
        return 0;
    }
}
