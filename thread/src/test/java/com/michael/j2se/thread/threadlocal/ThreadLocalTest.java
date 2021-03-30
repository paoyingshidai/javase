package com.michael.j2se.thread.threadlocal;

import org.junit.jupiter.api.Test;

/**
 * @author Michael
 */
public class ThreadLocalTest {

    @Test
    void test() {

        for (int i = 0; i < 16; i++) {
            ThreadLocal<String> threadLocal = new ThreadLocal<>();
            threadLocal.set("hello" + i);
            threadLocal.remove();
            System.out.println(threadLocal.get());
        }
    }

    class MyThreadLocal<T> extends ThreadLocal<T> {



    }
}
