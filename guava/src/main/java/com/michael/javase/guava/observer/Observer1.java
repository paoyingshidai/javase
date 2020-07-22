package com.michael.javase.guava.observer;

import com.google.common.eventbus.Subscribe;

/**
 * @auth Michael
 */
public class Observer1 {

    @Subscribe
    public void ob1Mthod1(String msg) {
        System.out.println(msg + " test1!");
    }

    @Subscribe
    public void ob1Method2(String msg) {
        System.out.println(msg + " test2!");
    }

}
