package com.michael.javase.guava.observer;

import com.google.common.eventbus.EventBus;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @auth Michael
 */
public class Main {

    public static void main(String[] args) {

        ExecutorService service = Executors.newCachedThreadPool();

        EventBus eventBus = new EventBus();
        Observer1 observer1 = new Observer1();
        Observer2 observer2 = new Observer2();

        eventBus.register(observer1);
        eventBus.register(observer2);

        // 只有注册的参数类型为String的方法会被调用


        for (int i = 0; i < 10; i++) {
            service.submit(() -> {
                eventBus.post("post string method");
            });
        }


        // 注销observer2
//        eventBus.unregister(observer2);
//        eventBus.post("post string method after unregister");

        service.shutdown();

    }

}
