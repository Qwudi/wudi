package com.hwq.spring.config;

/**
 * @Auther: haowenqiang
 * @Date: 2019/7/3
 * @Description:
 */
public class Car {
    private BenzCar benzCar;

    public Car(BenzCar car){
        this.benzCar = car;
    }

    public BenzCar getBenzCar() {
        return benzCar;
    }

    public void setBenzCar(BenzCar benzCar) {
        this.benzCar = benzCar;
    }
    static final int MAXIMUM_CAPACITY = 1 << 30;
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    public static void main(String[] args) {
        System.out.println(tableSizeFor(32));

    }
}
