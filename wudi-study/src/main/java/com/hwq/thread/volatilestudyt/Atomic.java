package com.hwq.thread.volatilestudyt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Auther: haowenqiang
 * @Description: 验证volatile不保证原子性
 */
class Data{
    //加不加volatile 多线程调用add是一个效果，num本身就不是原子操作，
    //说明volatile对于本身不是原子性的操作不保证其原子性
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void add(){
        /*
            ++操作是 【非原子操作】
            其字节码一目了然：
            public void add();
            Code:
               0: aload_0
               1: dup
               2: getfield      #2                  // Field num:I
               5: iconst_1
               6: iadd
               7: putfield      #2                  // Field num:I
              10: return
         */
        num++;
    }
    //解决非原子性问题：多线程对一个共享数的加操作使用juc中的 AtomicInteger  getAndIncrement方法
    public void atomicIncrement(){
        atomicInteger.getAndIncrement();
    }

}
public class Atomic {
    public static void main(String[] args) throws InterruptedException {
        Data data = new Data();
        CountDownLatch countDownLatch = new CountDownLatch(10);
        //新建10个线程每个线程执行1000次++操作 预期结果10000
        for(int i = 1; i<=10 ; i++){
            new Thread(()->{
                for(int j = 1 ; j <= 1000; j++){
                    //非原子性++
                    data.add();
                    //原子性++
                    data.atomicIncrement();
                }
                //countDownLatch -1
                countDownLatch.countDown();
            }).start();
        }
        //countDownLatch为0时主线程执行
        countDownLatch.await();
        //结果每次都不一样，都不到10000，说明不保证原子性
        System.out.println("非原子结果值：   " + data.num);
        System.out.println("原子结果值：   " + data.atomicInteger);
    }
}
