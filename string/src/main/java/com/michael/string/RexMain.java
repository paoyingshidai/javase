package com.michael.string;

import java.util.regex.Pattern;

public class RexMain {

    public static void main(String[] args) {

        Pattern compile = Pattern.compile("/a/");

        System.out.println(compile.matches("a", "a"));

    }

}
