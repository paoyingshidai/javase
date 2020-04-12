package com.michael.j2se.concurrent.asyn;

import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/qq_42606051/article/details/84028376
 */
public class MyCompletableFuture {

    public static void main(String[] args) {


        thenAccept();

        thenCombine();

        thenCompose();

        applyToEither();

        exceptionally();

        whenComplete();

        handle();

        allOfOrAnyOf();

        thenAcceptBoth();
    }

    public static void thenAcceptBoth() {
        System.out.println();
        System.out.println("thenAcceptBoth");

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("开始执行B");
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("返回 B");
            return "B";
        });
        CompletableFuture<Void> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("开始执行A");
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("返回 A");
            return "A";
        }).thenAcceptBoth(futureB, (s, s2) -> {
                    System.out.println(s);
                    System.out.println(s2);
        });

    }

    /**
     * allOf:当所有的CompletableFuture都执行完后执行计算
     * anyOf:最快的那个CompletableFuture执行完之后执行计算
     *
     * 场景二:查询一个商品详情,需要分别去查商品信息,卖家信息,库存信息,订单信息等,
     * 这些查询相互独立,在不同的服务上,假设每个查询都需要一到两秒钟,要求总体查询时间小于2秒.
     */
    public static void allOfOrAnyOf() {
        System.out.println();
        ExecutorService executorService = Executors.newFixedThreadPool(4);

        long start = System.currentTimeMillis();
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "商品详情";
        },executorService);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "卖家信息";
        },executorService);

        CompletableFuture<String> futureC = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "库存信息";
        },executorService);

        CompletableFuture<String> futureD = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(RandomUtils.nextInt(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "订单信息";
        },executorService);

        CompletableFuture<Void> allFuture = CompletableFuture.allOf(futureA, futureB, futureC, futureD);
//        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(futureA, futureB, futureC, futureD);
        allFuture.join();

        System.out.println(String.format("%s %s %s %s", futureA.join(), futureB.join(), futureC.join(), futureD.join()));
        System.out.println("总耗时:" + (System.currentTimeMillis() - start));
        // 等所有的人物执行完之后 才可以释放主线程。
    }

    /**
     * 功能:当CompletableFuture的计算结果完成，或者抛出异常的时候，可以通过handle方法对结果进行处理
     */
    public static void handle() {
        System.out.println();
        CompletableFuture<String> futureA = CompletableFuture.
                supplyAsync(() -> "执行结果:" + (100 / 0))
                .thenApply(s -> "apply result:" + s)
                .exceptionally(e -> {
                    System.out.println("ex:" + e.getMessage()); //java.lang.ArithmeticException: / by zero
                    return "futureA result: 100";
                })
                .handle((s, e) -> {
                    if (e == null) {
                        System.out.println(s);
                    } else {
                        System.out.println(e.getMessage());
                    }
                    return "handle result:" + (s == null ? "500" : s);
                });
        System.out.println(futureA.join()); //handle result:futureA result: 100

        System.out.println();
        System.out.println("先执行 handle 的情况");

        CompletableFuture<String> futureB = CompletableFuture.
                supplyAsync(() -> "执行结果:" + (100 / 0))
                .thenApply(s -> "apply result:" + s)
                .handle((s, e) -> {
                    if (e == null) {
                        System.out.println(s);//未执行
                    } else {
                        System.out.println(e.getMessage());//java.lang.ArithmeticException: / by zero
                    }
                    return "handle result:" + (s == null ? "500" : s);
                })
                .exceptionally(e -> {
                    System.out.println("ex:" + e.getMessage()); //未执行
                    return "futureA result: 100";
                });
        System.out.println(futureB.join());//handle result:500

        // 由此可见 handle 只处理上面处理步骤的结果，是有顺序限定的。


    }
    /**
     * 功能:当CompletableFuture的计算结果完成，或者抛出异常的时候，都可以进入whenComplete方法执行,举个栗子
     */
    public static void whenComplete() {
        System.out.println();
        CompletableFuture<String> futureA = CompletableFuture.
                supplyAsync(() -> "执行结果:" + (100 / 0))
                .thenApply(s -> "apply result:" + s)
                .whenComplete((s, e) -> {
                    if (s != null) {
                        System.out.println(s);//未执行
                    }
                    if (e == null) {
                        System.out.println(s);//未执行
                    } else {
                        System.out.println("whenComplete : " + e.getMessage());
                    }
                }).exceptionally(e -> {
                    System.out.println("exceptionally ex : " + e.getMessage());
                    return "futureA result: 100";
                });
        System.out.println(futureA.join());
    }

    /**
     * 功能:当运行出现异常时,调用该方法可进行一些补偿操作,如设置默认值.
     * <p>
     * 　　场景:异步执行任务A获取结果,如果任务A执行过程中抛出异常,则使用默认值100返回.
     */
    public static void exceptionally() {
        System.out.println();
        CompletableFuture<String> futureA = CompletableFuture.
                supplyAsync(() -> "执行结果:" + (100 / 0))
                .thenApply(s -> "futureA result:" + s)
                .exceptionally(e -> {
                    System.out.println(e.getMessage()); //java.lang.ArithmeticException: / by zero
                    return "futureA result: 100";
                });
        CompletableFuture<String> futureB = CompletableFuture.
                supplyAsync(() -> "执行结果:" + 50)
                .thenApply(s -> "futureB result:" + s)
                .exceptionally(e -> "futureB result: 100");
        System.out.println(futureA.join());//futureA result: 100
        System.out.println(futureB.join());//futureB result:执行结果:50
    }

    /**
     * 功能:执行两个CompletionStage的结果,那个先执行完了,就是用哪个的返回值进行下一步操作
     * 　　场景:假设查询商品a,有两种方式,A和B,但是A和B的执行速度不一样,我们希望哪个先返回就用那个的返回值.
     */
    public static void applyToEither() {
        System.out.println();
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "通过方式 A 获取商品";
        });
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "通过方式 B 获取商品";
        });
        CompletableFuture<String> futureC = futureA.applyToEither(futureB, product -> "结果:" + product);
        System.out.println(futureC.join()); //结果:通过方式A获取商品a
    }

    /**
     * 相当于 Stream 的 map
     */
    public static void thenCompose() {
        System.out.println();
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> "hello");
        CompletableFuture<String> futureB = futureA.thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " world"));
        CompletableFuture<String> future3 = futureB.thenCompose(s -> CompletableFuture.supplyAsync(s::toUpperCase));
        System.out.println(future3.join());
    }


    /**
     * 等待 A B 执行完后，在执行 C
     */
    public static void thenCombine() {
        System.out.println();
        CompletableFuture<Double> futurePrice = CompletableFuture.supplyAsync(() -> 100d);
        CompletableFuture<Double> futureDiscount = CompletableFuture.supplyAsync(() -> 0.8);
        CompletableFuture<Double> futureResult = futurePrice.thenCombine(futureDiscount, (price, discount) -> price * discount);
        System.out.println("最终价格为:" + futureResult.join()); //最终价格为:80.0

    }


    public static void thenAccept() {
        System.out.println();
        System.out.println("thenAccept ------------------");
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> "任务A").thenApply(String::toLowerCase);
        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            System.out.println("执行任务B");
            return "任务B";
        });
        CompletableFuture<String> futureC = futureB.thenApply(b -> {
            System.out.println("执行任务C.");
            System.out.println("参数:" + b);//参数:任务B
            return "a";
        });
        System.out.println(futureC.join());
    }

    /**
     * 流式调用
     */
    public static void thenAccept2() {
        System.out.println();
        CompletableFuture<Void> future = CompletableFuture
                .supplyAsync(() -> 50)
                .thenApply(i -> Integer.toString(i))
                .thenApply(str -> "\"" + str + "\"")
                .thenAccept(System.out::println);
        future.join();
    }

}
