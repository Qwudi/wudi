package com.hwq.arithmetic.array;

/**
 * @Auther: haowenqiang
 * @Description: 输入一个整型数组，数组里有正数也有负数。
 * 数组中一个或连续的多个整数组成一个子数组。
 * 求所有子数组的和的最大值。要求时间复杂度为O(n)。
 * 例如输入的数组为{1, -2, 3, 10, -4, 7, 2, -5}，和最大的子数组为3, 10, -4, 7, 2}。
 * 因此输出为该子数组的和18。
 */
public class MaxSubArray {

    //1.暴力穷举：依次计算所有可能出现的子数组的合，保留最大的
    //时间复杂度O（n^2）
    private static int method1(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        int maxSum = arr[0];
        int currSum;
        for (int i = 0; i < arr.length; i++) {
            currSum = 0;
            for (int j = i; j < arr.length; j++) {
                currSum += arr[j];
                if (currSum > maxSum) {
                    maxSum = currSum;
                }
            }
        }

        return maxSum;
    }

    //动态规划思想O(n)
    private static int method2(int[] arr) {
        if (arr.length == 0) {
            return 0;
        }
        //历代最强（保存所有连续子集的最大和）
        int maxSum = Integer.MIN_VALUE;
        //当前连续子集的和
        int currSum = arr[1];
        for (int i = 1; i < arr.length; i++) {
            //对于每个值，都有两个选择：1.划入上一连续子集 2.另起山头当下一个连续子集的头
            //判断条件： 当前子集的和是否对自己有益处。
            // 如果下一个值加上currSum 比自己都小，那应该抛弃累赘，否则加入上一连续子集
            currSum = currSum + arr[i] < arr[i] ? arr[i] : currSum + arr[i];
            maxSum = currSum > maxSum ? currSum : maxSum;
        }
        return maxSum;
    }

    public static void main(String[] args) {
        int[] data = {1, -2, 3, 10, -4, 7, 2, -5};
        int[] data2 = {-1, -2, -3, -10, -4, -7, -2, 10};
        System.out.println(method2(data2));
        System.out.println(method2(data));
    }
}
