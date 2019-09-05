package com.hwq.thread.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class SpinLockDemo {
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public void lock(){
        Thread thread = Thread.currentThread();
        while (!atomicReference.compareAndSet(null,thread)){

        }
        System.out.println(thread.getName()+"\t come in");
    }
    public void unLock(){
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread,null);
        System.out.println(thread.getName()+"\t out");
    }
    public static void main(String[] args) {
        SpinLockDemo demo = new SpinLockDemo();

        new Thread(()->{
            demo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                demo.unLock();
            }
        },"AAA").start();
        new Thread(()->{
            demo.lock();
            demo.unLock();
        },"BBB").start();
    }
}
