package com.michael.j2se.concurrent.asyn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CompletionService是Java8的新增接口，JDK为其提供了一个实现类ExecutorCompletionService。这个类是为线程池中Task的执行结果服务的，
 * 即为Executor中Task返回Future而服务的。CompletionService的实现目标是任务先完成可优先获取到，即结果按照完成先后顺序排序。
 */
public class MyCompletionService {

    public static void main(String[] args) {

        ExecutorService exs = Executors.newFixedThreadPool(5);
        try {
            int taskCount = 10;
            List<Integer> list = new ArrayList<>();

            CompletionService<Integer> completionService = new ExecutorCompletionService<>(exs);

            for (int i = 0; i < taskCount; i++) {
                completionService.submit(new Task(i + 1));
            }

            long start = System.currentTimeMillis();

            for (int i = 0; i < taskCount; i++) {
                Integer result = completionService.take().get();
//                System.out.println("任务i==" + result + "完成!" + new Date());
                list.add(result);
            }

            System.out.println("执行时间：" + (System.currentTimeMillis() - start));

            System.out.println("list=" + list);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exs.shutdown();
        }
    }

    static class Task implements Callable<Integer> {
        Integer i;

        public Task(Integer i) {
            super();
            this.i = i;
        }

        @Override
        public Integer call() throws Exception {
            if (i == 5) {
                Thread.sleep(5000);
            } else {
                Thread.sleep(1000);
            }
            System.out.println("线程：" + Thread.currentThread().getName() + "任务i=" + i + ",执行完成！");
            return i;
        }

    }
}
