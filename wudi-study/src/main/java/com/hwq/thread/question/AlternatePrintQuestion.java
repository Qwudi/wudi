package com.hwq.thread.question;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class AlternatePrintQuestion {
    private int count = 0;
    private volatile int flag = 0;
    private void print() throws InterruptedException {
        while (count <= 100){
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+"\t"+count);
                count++;
                this.notify();
                if(count <= 100){
                    this.wait();
                }
            }
        }
    }
    private synchronized void print1() throws InterruptedException {
        while (flag != 0){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+" "+count);
        count++;
        flag = 1;
        this.notify();
    }
    private synchronized void print2() throws InterruptedException {
        while (flag != 1){
            this.wait();
        }
        System.out.println(Thread.currentThread().getName()+" "+count);
        count++;
        flag = 0;
        this.notify();
    }

    public static void main(String[] args) throws InterruptedException {
        AlternatePrintQuestion a = new AlternatePrintQuestion();

        new Thread(()->{
            for (int i = 0; i <= 50; i++) {
                try {
                    a.print1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"偶数").start();
        new Thread(()->{
            for (int i = 0; i < 50; i++) {
                try {
                    a.print2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"奇数").start();
    }

}
