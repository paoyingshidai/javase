package com.michael.string;

import org.apache.commons.lang3.StringUtils;

public class StringMain {

    public static void main(String[] args) {
        String s = "result";

        StringBuffer buffer = new StringBuffer(s);
        buffer.reverse();

        StringUtils.reverse(s);





    }

}
