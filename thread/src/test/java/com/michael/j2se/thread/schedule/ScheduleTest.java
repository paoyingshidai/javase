package com.michael.j2se.thread.schedule;

import org.junit.After;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @auth Michael
 */
public class ScheduleTest {

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
    ExecutorService cacheExecutorService = Executors.newCachedThreadPool();
    ExecutorService fixExecutorService = new ThreadPoolExecutor(1, 3,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<Runnable>(26));


    @Test
    public void test1() throws InterruptedException {

        for (int i = 0; i < 29; i++) {
            fixExecutorService.submit(() -> {
                System.out.println(Thread.currentThread().getName());
            });
        }

        new CountDownLatch(1).await(1, TimeUnit.SECONDS);
    }


    /**
     * 已经在等待运行或者正在运行的任务是否应该中断
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {

        ScheduledFuture<?> schedule = executorService.schedule(() -> {
            System.out.println(Thread.currentThread().getName());
            System.out.println("Michael");
        }, 10, TimeUnit.SECONDS);

//        schedule.cancel(false);

        new CountDownLatch(1).await(11, TimeUnit.SECONDS);

        System.out.println("done");
    }

    @After
    public void close () {
        executorService.shutdown();
        fixExecutorService.shutdown();
    }

}
