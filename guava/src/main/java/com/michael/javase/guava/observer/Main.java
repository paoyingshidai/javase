package com.michael.javase.guava.observer;

import com.google.common.eventbus.EventBus;

/**
 * @auth Michael
 */
public class Main {

    public static void main(String[] args) {

        EventBus eventBus = new EventBus();
        Observer1 observer1 = new Observer1();
        Observer2 observer2 = new Observer2();

        eventBus.register(observer1);
        eventBus.register(observer2);

        // 只有注册的参数类型为String的方法会被调用
        eventBus.post("post string method");

        // 注销observer2
        eventBus.unregister(observer2);
        eventBus.post("post string method after unregister");


    }

}
