package com.mino.springlab.lecture6;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RabbitMQHeaderXDeath {

    private int count;
    private String exchange;
    private String queue;
    private String reason;
    private List<String> routingKeys;
    private Date time;
}
