package com.hwq.arithmetic.array;

/**
 * @Auther: haowenqiang
 * @Description:
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用 O(1) 额外空间的条件下完成。
 * 给定 nums = [0,0,1,1,1,2,2,3,3,4],
 * 函数应该返回新的长度 5, 并且原数组 nums 的前五个元素被修改为 0, 1, 2, 3, 4。
 * 你不需要考虑数组中超出新长度后面的元素。
 */
public class RemoveDuplicates26 {
    //思路:快慢指针法。遍历数组，fast每遍历一次走一步，
    //slow只有在与fast所指元素值不相等时走一步，同时将fast的元素值值赋给slow的元素值
    //slow始终指向不同元素数组的末尾。
    private static int removeRepeat(int[] arr) {
        int slow = 0;
        for (int fast = 1; fast < arr.length; fast++) {
            if (arr[fast] != arr[slow]) {
                slow++;
                arr[slow] = arr[fast];
            }
        }
        return slow + 1;
    }
    public static void main(String[] args) {
//        int[] nums = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
//        System.out.println(removeRepeat(nums));
        int a = 255555555;
        int b = 2000000000;
        int c = a+b;
        

    }
}
