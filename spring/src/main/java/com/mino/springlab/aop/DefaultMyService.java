package com.mino.springlab.aop;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultMyService implements MyService {

    @Async
    @Override
    public void doSomething() {
        System.out.println("Default My Service loaded");
    }
}
