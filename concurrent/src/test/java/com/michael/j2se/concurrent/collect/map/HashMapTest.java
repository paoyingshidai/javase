package com.michael.j2se.concurrent.collect.map;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

/**
 * @author Michael
 */
public class HashMapTest {

    @Test
    void test() {
        HashMap<String, String> map = new HashMap<>();
        map.put("username", "Michael");
    }


    @Test
    void test2() {

        int h = 0b1011;

        System.out.println((h >>> 1));
        System.out.println(h ^ (h >>> 1));
    }

}
