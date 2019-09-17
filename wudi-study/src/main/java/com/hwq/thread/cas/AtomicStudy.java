package com.hwq.thread.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class AtomicStudy {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.getAndIncrement());
        System.out.println(atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 1111));
        System.out.println(atomicInteger.compareAndSet(5, 2222));
    }
}
