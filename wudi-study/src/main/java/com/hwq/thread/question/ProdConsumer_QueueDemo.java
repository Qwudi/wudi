package com.hwq.thread.question;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Description:
 * Queue实现的消费者生产者模式
 */

public class ProdConsumer_QueueDemo {
    final BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(5);

    private void prod(){
        int i = 0;
        while (true){
            try {
                System.out.println(Thread.currentThread().getName()+"生产了"+i);
                blockingQueue.put(i++);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void consumer(){
        while (true){
            try {
                Integer i = blockingQueue.take();
                System.out.println(Thread.currentThread().getName()+"消费了"+i);
                TimeUnit.SECONDS.sleep(6);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ProdConsumer_QueueDemo demo = new ProdConsumer_QueueDemo();
        new Thread(demo::prod,"AA").start();
        new Thread(demo::consumer,"BB").start();
    }

}
