package com.hwq.thread.question;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Auther: haowenqiang
 * @Description: synchronized和Lock的区别：
 * lock可以绑定多个condition，可以实现分组唤醒，精确唤醒想运行的线程
 * 而不是像synchronized随机唤醒。
 * 问题：线程A打印1次，线程B打印2次，线程C打印三次，按顺序，执行10次
 */
class Resource{
    volatile int num = 1;
    synchronized void execute1(int n)  {
        while(num != n){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        sout(n);
        changeNum();
        this.notifyAll();
    }
    //=======================================================
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();
    void execute2(int n){
        lock.lock();
        try {
            switch (num){
                case 1 : c1.await();break;
                case 2 : c2.await();break;
                case 3 : c3.await();break;
            }
            sout(n);
            changeNum();
            switch (num){
                case 1 : c2.signal();break;
                case 2 : c3.signal();break;
                case 3 : c1.signal();break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    void sout(int n){
        for (int i = 0; i < n ; i++) {
            System.out.println(Thread.currentThread().getName()+i);
        }
    }
    void changeNum(){
        switch (num){
            case 1 : num = 2;break;
            case 2 : num = 3;break;
            case 3 : num = 1;break;
        }
    }
}
public class SynchornizedVsLock {
    static Resource resource = new Resource();
    static void executr1(){
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute1(1);
            }
        },"AAA").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute1(2);
            }
        },"BBB").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute1(3);
            }
        },"CCC").start();
    }
    static void execute2(){
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute2(1);
            }
        },"AAA").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute2(2);
            }
        },"BBB").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                resource.execute2(3);
            }
        },"CCC").start();
    }

    public static void main(String[] args) {
        execute2();
    }
}
