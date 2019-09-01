package com.michael.j2se.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁，实现交替打印 1 - 100
 */
public class LockFair implements Runnable {
    //创建公平锁
    private static ReentrantLock lock = new ReentrantLock(true);

    // 当前的序号
    public static int currentIndex = 0;

    public static int currentIndex2 = 0;

    @Override
    public void run() {
        while (true){
            lock.lock();
            try {
                if (currentIndex < 100) {
                    System.out.println("当前值：" + currentIndex++ + ", " + Thread.currentThread().getName() + " 获得锁");
                } else {
                    break;
                }
            } finally {
                lock.unlock();
            }
        }
    }


    /**
     * 交替打印 1 —— 100， 不使用 ReentranLock 公平锁;
     */
    public void print() {

        Runnable runnable  = new Runnable() {
            @Override
            public void run() {

                while(currentIndex2 <= 100) {

                    synchronized (this) {

                        notify();

                        System.out.println("当前值：" + currentIndex2++ + ", " + Thread.currentThread().getName() + " 获得锁");

                        try {
                            if (currentIndex2 <= 100) {
                                wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    System.out.println(" ------ ");

                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();
    }

    /**
     * 通过信号量来交替打印
     * @param semaphore
     * @param oSemaphore
     * @return
     */
    public static Thread method(Semaphore semaphore, Semaphore oSemaphore) {

        Runnable runnable  = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        semaphore.acquire();
                        if (val < 100) {
                            System.out.println(Thread.currentThread().getName() + " " + val++);
                            oSemaphore.release();
                        } else {
                            oSemaphore.release();
                            break;
                        }
                        System.out.println(" ------- ");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        return new Thread(runnable);
    }


    static Integer ai = new Integer(0);
    public static void method2(boolean b) {
        new Thread(() -> {
            while (true) {
                synchronized (LockFair.class) {
                    if (ai > 100) {
                        break;
                    }
                    if (ai % 2 == 0 == b) {
                        System.out.println(Thread.currentThread().getName() + " " + ai++);
                    }
                }
            }
        }).start();
    }


    public static void method3(boolean b) {
        new Thread(() -> {
            while (true) {
                if (ai > 100) {
                    break;
                }
                if (ai % 2 == 0 == b) {
                    System.out.println(Thread.currentThread().getName() + " " + ai++);
                }
                System.out.println(" ----- ");
            }
        }).start();
    }


    static int val = 0;
    public static void main(String []args) {

        LockFair lockFairTest = new LockFair();

//        lockFairTest.print();

//        Thread t1 = new Thread(lockFairTest);
//        t1.start();
//        Thread t2 = new Thread(lockFairTest);
//        t2.start();
//        Thread t3 = new Thread(lockFairTest);
//        t3.start();


//        Semaphore s1 = new Semaphore(1);
//        Semaphore s2 = new Semaphore(0);
//        Semaphore s3 = new Semaphore(0);
//
//        Thread t1 = method(s1, s2);
//        Thread t2 = method(s2, s3);
//        Thread t3 = method(s3, s1);
//
//        t1.start();
//        t2.start();
//        t3.start();


//        method2(true);
//        method2(fals);

//        method3(true);
//        method3(false);

    }
}