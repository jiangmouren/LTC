package com.mycompany.app;

/**
 * Created by eljian on 6/16/2017.
 */

/**
 * Question:
 * Implement SelectionSort
 */

/**
 * Analysis:
 * Selection sort is the most straight forward one.
 * Scan and pick the smallest in the first round,
 * pick the second smallest int he second round, etc.
 */
public class SelectionSort {
    public int[] selectionSort(int[] nums){
        if(nums==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(nums.length<2) return nums;
        int left = 0;
        while(left<nums.length-1){
            int min = nums[left];
            int min_idx = left;
            for(int i=left+1; i<nums.length; i++){
                if(nums[i]<min){
                    min = nums[i];
                    min_idx = i;
                }
            }
            int tmp = nums[left];
            nums[left] =  nums[min_idx];
            nums[min_idx] = tmp;
            left++;
        }
        return nums;
    }
}
