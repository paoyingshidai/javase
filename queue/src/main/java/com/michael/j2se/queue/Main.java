package com.michael.j2se.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {


    public static ArrayBlockingQueue<Result> queue = new ArrayBlockingQueue(8);

    Map<String, Result> cacheMap = new HashMap<>(64);

    /**
     * 向队列添加数据
     */
    public void provider() throws InterruptedException {

        for (int i = 0; i < 100; i++) {
            TimeUnit.SECONDS.sleep(1);
            Result result = new Result(i+"", "result"+i, System.currentTimeMillis());
            cacheMap.put(i+"", result);
            queue.offer(result);
        }
    }

    public void customer() throws InterruptedException {
        while (true) {

            Result take = queue.take();
            long expireTime = System.currentTimeMillis() - take.createTime;
            if (expireTime < 5000) {       // 超时的不做处理
                System.out.println(take.getResult());
                TimeUnit.SECONDS.sleep(2);
            }
        }
    }

    public void destroyCache() {
        for (int i = 0; i < 10; i++) {
            cacheMap.remove(i+"");
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Main main = new Main();

        // 监听消费
        new Thread(() -> {
            try {
                main.customer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();


        // 开始提供
        new Thread(() -> {
            try {
                main.provider();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 十秒后开始清除缓存
        TimeUnit.SECONDS.sleep(10);
        System.out.println("start to destroy cache");
        new Thread(() -> {
            main.destroyCache();
        }).start();

        System.out.println("end main");

    }

    @Setter
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    class Result {
        private String id;
        private String result;
        private Long createTime;
    }

}
