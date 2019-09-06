package com.hwq;

import com.hwq.spring.config.BenzCar;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class Test {
    public static void defangIPaddr(String address) {
        address = address.replace(".","[.]");
        System.out.println(address);
    }
    public static void testFinal(){
        final String fs = "sssss";
        fs.substring(1);
        System.out.println(fs);
        final BenzCar benzCar = new BenzCar();
        System.out.println(benzCar.toString());
        benzCar.setLen("2322");
        System.out.println(benzCar.toString());

    }
    public static void testPhoneNum(){
        String num = "18519750533";
        String s = num.replace(num.substring(3,7), "****");
        System.out.println(num);
    }
    public static void main(String[] args) {
//        defangIPaddr("1.1.1.1");
////        testFinal();
        System.out.println(3^2);
        System.out.println(1^2);
        testPhoneNum();
    }
}
