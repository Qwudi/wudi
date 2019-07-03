package com.hwq.thread.volatilestudyt;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Description: volatile可以保证见性测试
 */
class MyData{
    //加volatile
    volatile int i = 0;
    //不加volatile
//    int i = 0;

    public void change(){
        this.i =10;
    }

}
public class Visibility {
    public static void main(String[] args) {
        MyData myData = new MyData();
        //新建线程wudi，睡眠三秒等main线程读取变量，然后改变共享变量i,如果输出“已感知到”，说明wudi线程修改对main线程可见
        //如果程序进入死循环，不运行完毕也不输出，说明main线程无法感知共享变量改变，--不可见
        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(3);
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
