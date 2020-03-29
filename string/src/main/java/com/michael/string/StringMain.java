package com.michael.string;

public class StringMain {

    public static void reverse() {
        // 字符户反转
        String result = "result";

        char[] chars = result.toCharArray();
        char[] res = new char[chars.length];

        int i = 0, j = chars.length - 1;

        while(j >= 0) {
            res[i] = chars[j];
            i++;
            j--;
        }

        System.out.println(new String(res));
    }

    public static void main(String[] args) {

       reverse();

    }

}
