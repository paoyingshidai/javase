package com.michael.j2se.concurrent.asyn;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 实现了 Runnable Future 接口, 执行完 Runnable 的方法后，返回结果通过自身可以获取的到
 */
public class MyFutureTask {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService service = Executors.newFixedThreadPool(2);

        FutureTask task = new FutureTask(() -> {return 1;});

        service.submit(task);

        System.out.println(task.get());

        service.shutdown();
    }
}
