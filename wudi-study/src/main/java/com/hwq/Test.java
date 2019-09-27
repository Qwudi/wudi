package com.hwq;

import com.hwq.spring.config.BenzCar;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class Test {
    public static void defangIPaddr(String address) {
        address = address.replace(".", "[.]");
        System.out.println(address);
    }

    public static void testFinal() {
        final String fs = "sssss";
        fs.substring(1);
        System.out.println(fs);
        final BenzCar benzCar = new BenzCar();
        System.out.println(benzCar.toString());
        benzCar.setLen("2322");
        System.out.println(benzCar.toString());

    }

    public static void testPhoneNum() {
        String num = "18519750533";

        String s = num.replace(num.substring(3, 7), "****");
        System.out.println(num);
    }

    public static void main(String[] args) throws InterruptedException {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                2, 2,
                60,
                TimeUnit.MINUTES,
                new ArrayBlockingQueue<>(5),
                (r)-> new Thread(r,"my-threadPool"+"t_pl_pool_" + r.hashCode()),
                (r, executor) -> {
                    System.out.println(executor.getTaskCount());
                    System.out.println(executor.getActiveCount());
                    System.out.println("线程池超出");
                });
        for (int i = 0; i < 10; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName()+"开始执行");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
