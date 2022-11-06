package com.mino.springlab.lecture5;

import java.util.*;

public class RabbitMqHeader {

    private static final String KEYWORD_QUEUE_WAIT = "wait";
    private List<RabbitMQHeaderXDeath> xDeaths = new ArrayList<>(2);
    private String xFirstDeathExchange = "";
    private String xFirstDeathQueue = "";
    private String xFirstDeathReason = "";

    @SuppressWarnings("unchecked")
    public RabbitMqHeader(Map<String, Object> headers) {
        if (headers != null) {
            var xFirstDeathExchange = Optional.ofNullable(headers.get("x-first-death-exchange"));
            var xFirstDeathQueue = Optional.ofNullable(headers.get("x-first-death-queue"));
            var xFirstDeathReason = Optional.ofNullable(headers.get("x-first-death-reason"));

            xFirstDeathExchange.ifPresent(x -> this.xFirstDeathExchange = x.toString());
            xFirstDeathQueue.ifPresent(x -> this.xFirstDeathQueue = x.toString());
            xFirstDeathReason.ifPresent(x -> this.xFirstDeathReason = x.toString());

            var xDeathHeaders = (List<Map<String,Object>>) headers.get("x-death");

            if (xDeathHeaders != null) {
                for (Map<String, Object> x : xDeathHeaders) {
                    RabbitMQHeaderXDeath rhx = new RabbitMQHeaderXDeath();
                    var reason = Optional.ofNullable(x.get("reason"));
                    var count = Optional.ofNullable(x.get("count"));
                    var exchange = Optional.ofNullable(x.get("exchange"));
                    var queue = Optional.ofNullable(x.get("queue"));
                    var routingkeys = Optional.ofNullable(x.get("routingkeys"));
                    var time = Optional.ofNullable(x.get("time"));

                    reason.ifPresent(s -> rhx.setReason(s.toString()));
                    count.ifPresent(s -> rhx.setCount(Integer.parseInt(s.toString())));
                    exchange.ifPresent(s -> rhx.setExchange(s.toString()));
                    queue.ifPresent(s -> rhx.setQueue(s.toString()));
                    routingkeys.ifPresent(s -> {
                        var keyList = (List<String>) s;
                        rhx.setRoutingKeys(keyList);
                    });
                    time.ifPresent(s -> rhx.setTime((Date) s));

                    xDeaths.add(rhx);
                }
            }
        }
    }

    public int getFailedRetryCount() {
        for (var xDeath : xDeaths) {
            if (xDeath.getExchange().toLowerCase().endsWith(KEYWORD_QUEUE_WAIT)
            && xDeath.getQueue().toLowerCase().endsWith(KEYWORD_QUEUE_WAIT)) {
                return xDeath.getCount();
            }
        }
        return 0;
    }
}
