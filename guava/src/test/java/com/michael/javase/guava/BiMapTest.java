package com.michael.javase.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

/**
 * @auth Michael
 */
public class BiMapTest {

    @Test
    public void biMapTest() {
        BiMap<String, User> map = Maps.synchronizedBiMap(HashBiMap.create());

        User user = new User(12, "username");
        map.put(user.getUsername(), user);

        User ret = map.get(user.getUsername());
        System.out.println(ret);
        
        user.setUsername("user");

        String username = map.inverse().get(user);
        System.out.println(username);

    }

    /**
     * 需要重写 equals 和 hash 方法
     */
    @Data
    @AllArgsConstructor
    @EqualsAndHashCode(exclude = "username")
    class User {

        private Integer age;
        private String username;

    }
}
