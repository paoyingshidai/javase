package com.michael.javase.pattern;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PatternTest {

    @Test
    void test() throws Exception {

        Pattern pattern = Pattern.compile("/^(\\-?)(\\d+)$/");

        Matcher matcher = pattern.matcher("123");




    }


}