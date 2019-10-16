package com.hwq.thread.question;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class DeadLock {

    static final Object lock1 = new Object();
    static final Object lock2 = new Object();

    private static void method1() throws InterruptedException {
        synchronized (lock1){
            Thread.sleep(2000);
            synchronized (lock2){
                System.out.println("method1 end ...");
            }
        }
    }
    private static void method2() throws InterruptedException {
        synchronized (lock2){
            Thread.sleep(2000);
            synchronized (lock1){
                System.out.println("method2 end ...");
            }
        }
    }

    public static void main(String[] args) {
        new Thread(()->{
            try {
                method1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-1").start();
        new Thread(()->{
            try {
                method2();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"thread-2").start();
    }
}
