package com.michael.j2se.lock;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * @author Michael
 */
public class StampedLockTest {

    ExecutorService executorService = Executors.newFixedThreadPool(100);

    @Test
    void stampedLockTest() throws InterruptedException {

        StampedLock lock = new StampedLock();
        final Counter counter = new Counter(lock);

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                int j = 0;
                while (j < 1000) {
                    counter.add();
                    j++;
                }
            });
        }

        executorService.submit(() -> {
            int j = 0;
            while (j < 1000) {
                counter.print();
                j++;
            }
        });

        new CountDownLatch(1).await();
    }



    class Counter {

        StampedLock lock;

        int x = 0, y = 0;

        public Counter(StampedLock lock) {
            this.lock = lock;
        }

        public void add() {
            long l = lock.writeLock();
            try {
                x++;
                y++;
            } finally {
                lock.unlockWrite(l);
            }
        }

        public void print() {
            long l = lock.tryOptimisticRead();

            try {
                if (!lock.validate(l)) {
                    l = lock.readLock();
                    if (x != y) {
                        System.out.println("x = " + x + " y = " + y);
                    }
                }
            } finally {
                lock.unlockRead(l);
            }
        }
    }

}
