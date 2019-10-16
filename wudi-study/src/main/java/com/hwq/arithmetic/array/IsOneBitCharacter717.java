package com.hwq.arithmetic.array;

/**
 * @Auther: haowenqiang
 * @Description:
 * 有两种特殊字符。第一种字符可以用一比特0来表示。第二种字符可以用两比特(10 或 11)来表示。
 * 现给一个由若干比特组成的字符串。问最后一个字符是否必定为一个一比特字符。给定的字符串总是由0结束。
 * 输入:
 * bits = [1, 0, 0]
 * 输出: True
 * 解释:
 * 唯一的编码方式是一个两比特字符和一个一比特字符。所以最后一个字符是一比特字符。
 * 输入:
 * bits = [1, 1, 1, 0]
 * 输出: False
 * 解释:
 * 唯一的编码方式是两比特字符和两比特字符。所以最后一个字符不是一比特字符。
 */
public class IsOneBitCharacter717 {
    public boolean isOneBitCharacter(int[] bits) {
        int i = 0;
        while (i < bits.length){
            if(bits[i] == 0){
                i++;
                if(i == bits.length){
                    return true;
                }
            }else{
                i+=2;
                if(i>bits.length){
                    return false;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] arr = {1,0,0};
        System.out.println(new IsOneBitCharacter717().isOneBitCharacter(arr));
    }
}
