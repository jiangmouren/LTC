package com.mycompany.app;

/**
 * Created by eljian on 6/16/2017.
 */

/**
 * Question:
 * Implement the Bubble Sort.
 */

/**
 * Analysis:
 * Starting from left to right swap if order is wrong.
 * By the end of the first round, the largest element is guaranteed moved to the end of the queue.
 * In the next iteration, we only need to loop to the n-2 position.
 */
public class BubbleSort {
    public int[] bubbleSort(int[] nums){
        if(nums==null) throw new IllegalArgumentException("inputs cannot be null");
        if(nums.length<2) return nums;
        int right = nums.length-1;
        while(right>0){
            int left = 0;
            while(left<right){
                if(nums[left]>nums[left+1]){
                    int tmp = nums[left];
                    nums[left] = nums[left+1];
                    nums[left+1] = tmp;
                }
                left++;
            }
            right--;
        }
        return nums;
    }
}
