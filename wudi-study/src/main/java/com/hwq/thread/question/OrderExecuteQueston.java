package com.hwq.thread.question;

/**
 * @Auther: haowenqiang
 * @Description:线程顺序执行
 */
public class OrderExecuteQueston {
    static volatile int num = 1;
    static final Object object = new Object();
    private static void execute1() {
        System.out.printf("[%s] \t执行完毕\n", Thread.currentThread().getName());
    }

    static Thread threadA = new Thread(OrderExecuteQueston::execute1, "A");
    static Thread threadB = new Thread(OrderExecuteQueston::execute1, "B");
    static Thread threadC = new Thread(OrderExecuteQueston::execute1, "C");

    private static void method1() throws InterruptedException {
        threadA.start();
        threadA.join();
        threadB.start();
        threadB.join();
        threadC.start();
        threadC.join();
    }

    private static void method2() {
        threadA.start();
        while (threadA.isAlive()) {

        }
        threadB.start();
        while (threadB.isAlive()) {

        }
        threadC.start();
        while (threadC.isAlive()) {

        }
    }


    public static void main(String[] args) throws Exception {
        method1();
    }
}
