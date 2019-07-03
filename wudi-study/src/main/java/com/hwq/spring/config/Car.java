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
}
