package com.hwq.thread.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class Cache{
    private volatile Map<String,Object> map = new HashMap<>();
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void set(String key,Object value){
        rwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在写入"+key);
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            map.put(key,value);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.writeLock().unlock();
        }
    }
    public void get(String key){
        rwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName()+"\t 正在读取:");
            try {TimeUnit.MILLISECONDS.sleep(300);} catch (InterruptedException e) {e.printStackTrace();}
            Object result = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成"+result);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            rwLock.readLock().unlock();
        }
    }
}
/**
 * @Auther: haowenqiang
 * @Description: 读写锁demo
 * 对 读-读 操作不阻塞
 * 读-写，写-读操作不能同时操作
 */
public class ReadWroteLockDemo {

    public static void main(String[] args) {
       Cache cache = new Cache();
       for (int i = 1;i<=5;i++){
           int finalI = i;
           new Thread(()->{
              cache.set(finalI +"",finalI+"");
          },i+"").start();
       }
       for (int i = 1;i<=5;i++){
           int finalI = i;
           new Thread(()->{
              cache.get(finalI +"");
          },i+"").start();
       }
    }
}
