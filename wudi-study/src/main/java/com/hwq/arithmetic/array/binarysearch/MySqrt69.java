package com.hwq.arithmetic.array.binarysearch;

/**
 * @Auther: haowenqiang
 * @Description:
 * 实现 int sqrt(int x) 函数。
 *
 * 计算并返回 x 的平方根，其中 x 是非负整数。
 *
 * 由于返回类型是整数，结果只保留整数的部分，小数部分将被舍去。
 *
 */
public class MySqrt69 {
    /*二分法变种问题
        找出最后一个小于等于目标值的数
    */
    public int mySqrt(int x) {
        long left = 0,right = x;
        while(left <= right){
            long mid = left + ((right - left)>>2);

            if(mid * mid > x ){
                right = mid - 1;
            }else{
                if(mid == x || (mid+1)*(mid+1) > x){
                    return (int)mid;
                }else{
                    left = mid + 1;
                }
            }
        }
        return -1;
    }
}
