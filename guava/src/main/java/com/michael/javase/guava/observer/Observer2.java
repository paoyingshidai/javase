package com.michael.javase.guava.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @auth Michael
 */
public class Observer2 {

    @Subscribe
    public void ob2Method1(String msg) {
        System.out.println(msg + " test3!");
    }

    /**
     * 参数类型要匹配
     * @param msg
     */
    @Subscribe
    public void ob2Method2(Integer msg) {
        System.out.println(msg + " test4!");
    }
}
