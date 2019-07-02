package com.hwq.thread.volatilestudyt;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/2 0002 21:43
 * @Description: 可见性测试
 */
class MyData{
    volatile int i = 0;

    public void change(){
        this.i =10;
    }

}
public class Visibility {
    public static void main(String[] args) {
        MyData myData = new MyData();
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                myData.change();
                System.out.println(Thread.currentThread().getName()+"  change to  " + myData.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"wudi").start();
        while (true){
            if(myData.i == 10){
                System.out.println(Thread.currentThread().getName()+"已感知到！value="+myData.i);
                break;
            }
        }
    }
}
