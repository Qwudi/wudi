package com.hwq.thread.concurrent;

import java.util.concurrent.*;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class Futures {
    private static ExecutorService executors = Executors.newFixedThreadPool(10);
    static void demo1() throws ExecutionException, InterruptedException {
        final Future<Integer> submit = executors.submit(() -> {
            TimeUnit.SECONDS.sleep(200);
            return 100;
        });
        System.out.println(submit.get());
        System.out.println(submit.isDone());
        executors.shutdown();
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        demo1();
    }
}
