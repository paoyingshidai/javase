package com.michael.disruptor.disruptor;

import com.lmax.disruptor.EventHandler;
import com.michael.disruptor.db.Content;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

public class LongEventHandler implements EventHandler<LongEvent> {

    @Autowired
    private Content content;

    @Override
    public void onEvent(LongEvent longEvent, long l, boolean b) {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        content.setCount(content.getCount() + 1);
        System.out.println(content.getCount());
    }

}