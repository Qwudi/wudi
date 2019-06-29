package com.hwq.study;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @Auther: haowenqiang
 * @Date: 2019/6/17
 * @Description:
 */
public class Test {
    public static void test1(){
        float a = 1.0f - 0.9f;
        float b = 0.9f - 0.8f;
        System.out.println(a);
        System.out.println(b);
        if(a == b){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }
    public static void test2(){
        Float a = Float.valueOf(1.0f-0.9f);
        Float b = Float.valueOf(0.9f-0.8f);
        System.out.println(a);
        System.out.println(b);
        System.out.println(Float.floatToIntBits(a));
        System.out.println(Float.floatToIntBits(b));
        if(a.equals(b)){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }
    public static void test3() {
        String param = null;
        switch (param) {
            case "null":
                System.out.println("null");
                break;
            default:
                System.out.println("default");
        }
    }
    public static void test4() {
        BigDecimal b = new BigDecimal(0.1);
    }
    public static int[][] flipAndInvertImage(int[][] A) {
        for(int i = 0;i < A.length; i++){
            int[] a = A[i];
            int len = a.length;
            //反转
            for(int j = 0; j < len;j++){
                if(j < len/2){
                    int temp = a[j];
                    a[j] = a[len-j-1];
                    a[len-j-1] = temp;
                }
                if(a[j] == 0){
                    a[j] =1;
                }else if(a[j] == 1){
                    a[j] =0;
                }
            }
        }
        return A;
    }
    public static  void test5(){
        String[] m = {".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--","-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."};
        String[] words = {"gin", "zen", "gig", "msg"};
        Set<String> s = new HashSet<>();
        for(int i = 0;i<words.length;i++){
            StringBuilder sb = new StringBuilder();
            for(int j = 0;j<words[i].length();j++){
                sb.append(m[words[i].charAt(j)-'a']);
            }
            s.add(sb.toString());
        }
        System.out.println(s.size());

    }
    public static void main(String[] args) {
//        test1();
//        char a = ')';
//
//        System.out.println(a == ')');
//        int[][] A = {{1,1,0},{1,0,1},{0,0,0}};
//        flipAndInvertImage(A);
        test5();
    }
}
