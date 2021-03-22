package com.michael.j2se.concurrent.join;

import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class JoinTest {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    void executorCompletionServiceTest() throws InterruptedException, ExecutionException {

        ExecutorCompletionService<Result> completionService = new ExecutorCompletionService<>(executorService);

        for (int i = 0; i < 10; i++) {
            final int j = i;
            completionService.submit(() -> {
                // 模拟延时执行过程
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return new Result(j);
            });
        }

        for (int i = 0; i < 10; i++) {

            Future<Result> take = completionService.take();
            Result result = take.get();
            System.out.println(result.getUsername());
        }
    }


    public class Result {

        int i;

        public Result(int i) {
            this.i = i;
        }

        private String username = "username";

        public String getUsername() {
            return username + "_" + i;
        }
    }

}