package com.hwq.thread.blockQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Auther: haowenqiang
 * @Description:
 * 阻塞队列
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        //三组api add()/remove()抛异常
        // offer()满了就返回false/poll()空了就取出null
        // put()/take()满了或没有就阻塞
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));//true
        System.out.println(blockingQueue.add("b"));//true
        System.out.println(blockingQueue.add("c"));//true
        //满了再加 异常：java.lang.IllegalStateException: Queue full
        //System.out.println(blockingQueue.add("d"));
        //remove 先进先出
        System.out.println(blockingQueue.remove());//true
        System.out.println(blockingQueue.remove());//true
        System.out.println(blockingQueue.remove());//true
        //空了再移除 java.util.NoSuchElementException
        System.out.println(blockingQueue.remove());//false
    }

}
