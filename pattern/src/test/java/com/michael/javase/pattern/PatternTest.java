package com.michael.javase.pattern;

import com.michael.javase.pattern.extension.YamlFileSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

class PatternTest {

    static Stream<Arguments> baseTestArgs() {
        return Stream.of(
                arguments("12341234"),
                arguments("1z")
        );
    }
    @DisplayName("baseTest")
    @ParameterizedTest
    @MethodSource("baseTestArgs")
    void baseTest(String test) throws Exception {

        Pattern pattern = Pattern.compile("^[0-9]{1,}$");
        Matcher matcher = pattern.matcher(test);
        System.out.println(matcher.matches());
    }


    static Stream<Arguments> baseTest2Args() {
        return Stream.of(
                arguments("a"),
                arguments("0"),
                arguments("1"),
                arguments("2")
        );
    }
    @DisplayName("baseTest2")
    @ParameterizedTest
    @MethodSource("baseTest2Args")
    void baseTest2(String test) throws Exception {

        Pattern pattern = Pattern.compile("^[0-1]{1}$");
        Matcher matcher = pattern.matcher(test);
        System.out.println(matcher.matches());
    }

    @DisplayName("commonTest")
    @ParameterizedTest
    @YamlFileSource(resources = {"/common_pattern.yaml"}, targetClass = PatternArgs.class)
    void commonTest(PatternArgs args) throws Exception {

        Pattern pattern = Pattern.compile(args.getPattern());


        List<Result> results = new ArrayList<>();

        for (String test : args.getTests()) {
            Matcher matcher = pattern.matcher(test);
            Boolean bo;
            if (!matcher.matches()) {
                bo = Boolean.FALSE;
            } else {
                bo = Boolean.TRUE;
            }
            Result result = new Result();
            result.setResult(bo);
            result.setPattern(args.getPattern());
            result.setTest(test);
            results.add(result);
        }

        System.out.println("pattern = " + args.pattern);
        for (Result result : results) {
            System.out.println(result.getResult() + " " + result.getTest());
        }
        if (results.stream().filter(result -> !result.getResult()).count() > 0) {
            Assertions.assertTrue(false);
        }
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PatternArgs {

        private String pattern;

        private List<String> tests;
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Result {

        private Boolean result;
        private String pattern;
        private String test;
    }

}