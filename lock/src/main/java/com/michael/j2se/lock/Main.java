package com.michael.j2se.lock;

import java.util.concurrent.TimeUnit;

public class Main {


    public static void main(String[] args) {

        Thread t = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("线程 t");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();

        synchronized (t) {
            try {
                t.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("main Thread");
    }


}
