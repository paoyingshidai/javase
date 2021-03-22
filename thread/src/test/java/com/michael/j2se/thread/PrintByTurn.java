package com.michael.j2se.thread;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

/**
 * 交替打印
 * @author Michael
 */
public class PrintByTurn {


    /**
     * 字母数字相间输出
     * @throws InterruptedException
     */
    @Test
    void test() throws InterruptedException {

        Object lock = new Object();

        PrintLetter printLetter = new PrintLetter(lock);
        PrintNum printNum = new PrintNum(lock);

        new Thread(printLetter).start();
        new Thread(printNum).start();

        new CountDownLatch(1).await();
    }

    /**
     * 两个线程相间输出 100
     * @see com.michael.j2se.lock.LockFair#print()
     */
    @Test
    void test2() {

        new Runnable() {
            @Override
            public void run() {

            }
        };


    }

    class PrintLetter implements Runnable {

        Object lock;

        String[] letters = {"A", "B", "C", "D"};

        public PrintLetter(Object lock) {
            this.lock = lock;
        }

        @SneakyThrows
        @Override
        public void run() {

            synchronized (lock) {
                for (String letter : letters) {
                    lock.notify();
                    System.out.println(letter);
                    lock.wait();
                }
            }
        }
    }


    class PrintNum implements Runnable {

        Object lock;

        int[] number = {1, 2, 3, 4, 5};

        public PrintNum(Object lock) {
            this.lock = lock;
        }

        @SneakyThrows
        @Override
        public void run() {

            synchronized (lock) {

                for (int i : number) {
                    lock.notify();
                    System.out.println(i);
                    lock.wait();
                }

            }
        }
    }

}
