package com.michael.j2se.lock;


import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    private ReentrantLock lock = new ReentrantLock();
    Condition condition1 = lock.newCondition();
    Condition condition2 = lock.newCondition();

    private volatile boolean signal = false;

    ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Test
    public void test() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    lock.lock();
                    lock.tryLock(1, TimeUnit.SECONDS);
                    System.out.println("hello");
//                    TimeUnit.SECONDS.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            });
        }

        new CountDownLatch(1).await();
        executorService.shutdown();


    }

    /**
     * 使用 condition 实现交替打印, 这种打印与
     * {@link LockFair#printUsingCondition()} 打印有区别
     * 前者是可以实现控制指定的线程的 condition, 后者只能控制当前线程
     * 的 condition，前者的实现是 通过定义多个 condition，然后不同的
     * 线程之间进行交替的控制。个人理解，一个condition 相当于 在方法里
     * 使用 synchronized, 使用多个 condition 相当于在类中使用
     * synchronized，condition 相比于 synchronized， 前者可以跨越
     * 类，而在不同的类的方法中实现线程通信。
     */
    @Test
    public void testAlternate() {

        new Thread(new PrintAbc()).start();
        new Thread(new Print123()).start();
    }

    class PrintAbc implements Runnable {

        String[] abc = {"a", "b", "c"};

        @Override
        public void run() {

            try {
                lock.lock();
                for (String s : abc) {

                    if (signal) {
                        condition1.await();
                    }
                    System.out.println(s);
                    signal = !signal;
                    condition1.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    class Print123 implements Runnable {

        int[] _123 = {1, 2, 3};

        @Override
        public void run() {

            try {
                lock.lock();
                for (int i : _123) {
                    if (!signal) {
                        condition1.await();
                    }
                    System.out.println(i);
                    signal = !signal;
                    condition1.signal();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}