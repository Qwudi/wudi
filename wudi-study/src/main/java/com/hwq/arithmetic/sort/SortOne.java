package com.hwq.arithmetic.sort;

import java.util.Arrays;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class SortOne {

    private static int[] bubbleSort(int[] nums) {
        int len = nums.length;
        if (len <= 1) return nums;
        for (int i = 0; i < len; i++) {
            boolean isChange = false;
            for (int j = 0; j < len - i - 1; j++) {
                if (nums[j] > nums[j + 1]) {
                    int temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    isChange = true;
                }
            }
            if(!isChange) break;
        }

        return nums;
    }
    static int[] insertSort(int[] nums){
        int len = nums.length;
        if(len<=1) return nums;
        for(int i = 1;i<len;i++){
            int val = nums[i];
            int j = i-1;
            for(;j>=0;j--){
                if(nums[j] > val){
                    nums[j+1] = nums[j];
                }else{
                    break;
                }
            }
            nums[j+1] = val;
        }
        return nums;
    }
    static int[] selectSort(int[] arr){
        int len = arr.length;
        if(len <=1) return arr;
        for(int i = 0;i<len;i++){
            int min = arr[i];
            int minIndex = i;
            for(int j = i;j<len;j++){
                if(arr[j] < min){
                    min = arr[j];
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] =temp;
        }
        return arr;
    }
    public static void main(String[] args) {
        int[] nums = new int[]{3,5,4,1,2,6};
//        System.out.println(Arrays.toString(bubbleSort(nums)));
//        System.out.println(Arrays.toString(insertSort(nums)));
        System.out.println(Arrays.toString(selectSort(nums)));
    }
}
