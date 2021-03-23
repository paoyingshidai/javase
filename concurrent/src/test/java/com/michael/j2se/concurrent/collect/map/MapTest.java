package com.michael.j2se.concurrent.collect.map;

import lombok.ToString;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.locks.StampedLock;

/**
 * @author Michael
 */
public class MapTest {


    @Test
    void concurrentHashMapTest() {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(3, "3");
        map.put(9, "9");
        map.put(2, "2");
        map.put(4, "4");
        map.put(7, "7");

//        StampedLock

        System.out.println(map.get(3));

    }

    @Test
    void mapTest() {

        ConcurrentHashMap<Integer, String> map = new ConcurrentHashMap<>();

        map.put(3, "3");
        map.put(9, "9");
        map.put(2, "2");
        map.put(4, "4");
        map.put(7, "7");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }

        // 经过排序
        HashMap<Integer, String> map1 = new HashMap<>();
        map1.put(3, "3");
        map1.put(9, "9");
        map1.put(2, "2");
        map1.put(4, "4");
        map1.put(7, "7");

        for (Map.Entry<Integer, String> entry : map1.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }

        // 保持插入顺序
        LinkedHashMap<Integer, String> map2 = new LinkedHashMap<>();
        map2.put(3, "3");
        map2.put(9, "9");
        map2.put(2, "2");
        map2.put(4, "4");
        map2.put(7, "7");
        for (Map.Entry<Integer, String> entry : map2.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }
    }

    @Test
    void ConcurrentSkipListMapTest() {

        ConcurrentSkipListMap<Integer, String> map = new ConcurrentSkipListMap<>();

        map.put(3, "3");
        map.put(9, "9");
        map.put(2, "2");
        map.put(4, "4");
        map.put(7, "7");

        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            System.out.println("key = " + entry.getKey() + " value = " + entry.getValue());
        }

    }

}
