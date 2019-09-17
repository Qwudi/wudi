package com.hwq.thread.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class CountDownLanchDemo {
    private static CountDownLatch countDownLatch = new CountDownLatch(5);

    private static void countDownLanchTest() throws InterruptedException {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                    System.out.printf("线程[%s] \t 开始执行\n", Thread.currentThread().getName());
                    System.out.printf("线程[%s] \t 执行完毕\n", Thread.currentThread().getName());
                    countDownLatch.countDown();
            },i+"").start();
        }
        countDownLatch.await();
        System.out.println("全部执行完成");

    }

    public static void main(String[] args) throws InterruptedException {
        countDownLanchTest();
    }
}
