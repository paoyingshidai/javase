package com.michael.disruptor.service;

import com.michael.disruptor.db.Content;
import com.michael.disruptor.disruptor.LongEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class LockService {

    @Autowired
    private LongEventProducer longEventProducer;

    @Autowired
    private Content content;

    private ReentrantLock lock = new ReentrantLock();


    public void noLock() {
        content.setCount(content.getCount() + 1);
    }

    /**
     * 通过上锁的方式增加 count
     */
    public void addThroughLock() {

        try {
            lock.lock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            content.setCount(content.getCount() + 1);
        } finally {
            lock.unlock();
        }
    }


    public void addThroughDisruptor() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, 1L);
        longEventProducer.onData(bb);
    }

    public void addUsingTranslate() {
        ByteBuffer bb = ByteBuffer.allocate(8);
        bb.putLong(0, 1L);
        longEventProducer.onDataTranslate(bb);
    }

    public Integer getCount() {
        return content.getCount();
    }

}
