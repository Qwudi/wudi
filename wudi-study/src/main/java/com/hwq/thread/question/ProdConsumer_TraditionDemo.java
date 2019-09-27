package com.hwq.thread.question;

/**
 * @Auther: haowenqiang
 * @Description:
 * 传统版的消费者生产者模式
 * 题目 ： 一个初始值为0的变量，两个线程交替操作，一个加1一个减1，循环5次
 * 生产一个消费一个，同一时间最多只有一个
 */

public class ProdConsumer_TraditionDemo {
    private int num = 0;

    private synchronized void increment() throws InterruptedException {
        //wait() api中已经说明：wait放到while循环里，不能放if里，防止虚假唤醒，
        while (num != 0) {
            this.wait();
        }
        //干活
        num++;
        //通知
        System.out.printf("线程[%s]加:\t[%s] \n",Thread.currentThread().getName(),num);
        this.notify();
    }

    private synchronized void decrement() throws InterruptedException {
        while (num == 0) {
            this.wait();
        }
        num--;
        System.out.printf("线程[%s]减:\t[%s] \n",Thread.currentThread().getName(),num);
        this.notify();
    }

    public static void main(String[] args) {
        ProdConsumer_TraditionDemo demo = new ProdConsumer_TraditionDemo();
        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                try {
                    demo.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(()->{
            for (int i = 0; i <5 ; i++) {
                try {
                    demo.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }

}
