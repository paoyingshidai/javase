package com.michael.j2se.concurrent.join;

import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Michael
 */
public class CompletableFutureTest {

    @Test
    void test() {

        List<Result> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            list.add(new Result(i));
        }

        CompletableFuture<Result>[] completableFutures = list.stream().map(e ->
                CompletableFuture.supplyAsync(() -> handleResult(e)))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(completableFutures).join();

    }


    public Result handleResult(Result result) {
        result.setUsername("uuu");
        return result;
    }

    public class Result {

        int i;

        public Result(int i) {
            this.i = i;
        }

        @Setter
        private String username = "username";

        public String getUsername() {
            return username + "_" + i;
        }
    }
}
