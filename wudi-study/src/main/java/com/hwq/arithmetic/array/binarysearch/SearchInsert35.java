package com.hwq.arithmetic.array.binarysearch;

/**
 * @Auther: haowenqiang
 * @Description:
 */
public class SearchInsert35 {
    static int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length-1;
        while(left<=right){
            int mid = left+((right-left)>>1);
            if(nums[mid] >= target){
                if(mid ==0||nums[mid-1]<target){
                    return mid;
                }else{
                    right = mid -1;
                }
            }else {
                left = mid+1;
            }
        }
        return -1;
    }
    public static int searchInsert1(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while(left <= right) {
            int mid = (left + right) / 2;
            if(nums[mid] == target) {
                return mid;
            } else if(nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

}
