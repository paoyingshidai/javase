package com.michael.disruptor.zconfig;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.michael.disruptor.disruptor.LongEvent;
import com.michael.disruptor.disruptor.LongEventFactory;
import com.michael.disruptor.disruptor.LongEventHandler;
import com.michael.disruptor.disruptor.LongEventProducer;
import com.michael.disruptor.support.StringEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
public class MyConfiguration {

    @Bean
    public Disruptor disruptor() {
        Executor executor = Executors.newCachedThreadPool();
        LongEventFactory factory = new LongEventFactory();
        int bufferSize = 1024;
        Disruptor disruptor = new Disruptor<>(factory, bufferSize, executor);

        // 这里可以保证处理的顺序
        disruptor.handleEventsWith(longEventHandler()).then(stringEventHandler());
        disruptor.setDefaultExceptionHandler(exceptionHandler());
        disruptor.start();
        return disruptor;
    }

    @Bean
    public LongEventProducer longEventProducer() {
        RingBuffer ringBuffer = disruptor().getRingBuffer();
        LongEventProducer producer = new LongEventProducer(ringBuffer);
        return producer;
    }

    @Bean
    public ExceptionHandler exceptionHandler() {

        ExceptionHandler handler = new ExceptionHandler() {
            @Override
            public void handleEventException(Throwable ex, long sequence, Object event) {

                System.out.println();
            }

            @Override
            public void handleOnStartException(Throwable ex) {

            }

            @Override
            public void handleOnShutdownException(Throwable ex) {

            }
        };
        return handler;
    }

    @Bean
    public LongEventFactory longEventFactory() {
        return new LongEventFactory();
    }


    @Bean
    public LongEventHandler longEventHandler() {
        return new LongEventHandler();
    }

    @Bean
    public EventHandler stringEventHandler() {
        return (event, sequence, endOfBatch) -> {
            System.out.println(((LongEvent) event).getValue());
        };
    }
}
