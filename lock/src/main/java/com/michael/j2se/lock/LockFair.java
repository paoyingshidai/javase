package com.michael.j2se.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
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

    public static int currentIndex3 = 0;

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
     * 使用 Condition 进行控制
     */
    public void printUsingCondition() {

        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();

        Runnable runnable  = () -> {

            while (currentIndex3 <= 100) {
                lock.lock();
                condition.signal();
                System.out.println("当前值：" + currentIndex3++ + ", " + Thread.currentThread().getName() + " 获得锁");

                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };
        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

    }


    /**
     * 使用 Condition 进行控制 3 个线程交替打印
     */
    public void printUsingCondition2() {

        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();
        Condition condition3 = lock.newCondition();

        Runnable runnable1  = () -> {
            while (currentIndex3 <= 100) {
                lock.lock();
                condition1.signal();
                System.out.println("当前值：" + currentIndex3++ + ", " + Thread.currentThread().getName() + " 获得锁");

                try {
//                    condition1.await();   // 这个 await() 调用了多次，需要 singnal 多次
                    condition2.signal();
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };

        Runnable runnable2  = () -> {
            while (currentIndex3 <= 100) {
                lock.lock();
                condition2.signal();
                System.out.println("当前值：" + currentIndex3++ + ", " + Thread.currentThread().getName() + " 获得锁");

                try {
                    condition1.await();
//                    condition2.await();
                    condition3.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };

        Runnable runnable3  = () -> {
            while (currentIndex3 <= 100) {
                lock.lock();
                condition3.signal();
                System.out.println("当前值：" + currentIndex3++ + ", " + Thread.currentThread().getName() + " 获得锁");

                try {
                    condition1.signal();
                    condition2.await();
//                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        };
        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        Thread t3 = new Thread(runnable3);
        t1.start();
        t2.start();
        t3.start();
    }


    /**
     * 交替打印 1 —— 100， 不使用 ReentranLock 公平锁;
     *
     *  notify(); 与 condition#signal() 有同样的作用，但是condition更有利于线程之间的通信，
     *  调用更加灵活
     *
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
                                System.out.println(Thread.currentThread().getName() + " start wait");
                                wait();
                                System.out.println(Thread.currentThread().getName() + " end wait");
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

//        lockFairTest.printUsingCondition2();
//        lockFairTest.printUsingCondition();

        lockFairTest.print();

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