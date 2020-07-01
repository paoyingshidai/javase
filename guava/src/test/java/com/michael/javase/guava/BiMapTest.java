package com.michael.javase.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
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
        
        user.setUsername("username");

        String username = map.inverse().get(user);
        System.out.println(username);

    }

    @Data
    @AllArgsConstructor
    class User {

        private Integer age;
        private String username;

        // TODO 重写 equals 必须要重写 hash 方法
        @Override
        public boolean equals(Object obj) {
            if (obj instanceof User) {
                return this.age == ((User) obj).getAge();
            }
            return false;
        }
    }
}
