package com.mycompany.app.sort;

/**
 * Created by jiangmouren on 6/18/17.
 */

/**
 * Question:
 * Implement Insertion Sort.
 */

/**
 * Analysis:
 * Insertion Sort is to loop from left to right and every time just insert the new number into the correct place.
 * Left side sub-array are sorted.
 * Worst and Average Cases are O(n^2), Best Case is O(n), when it is already sorted.
 * Need to do a shift for the Insertion.
 * Insertion sort is a good option for nearly sorted arrays.
 */
public class InsertionSort {
    public int[] insertionSort(int[] nums){
        if(nums==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(nums.length<2) return nums;
        for(int i=1; i<nums.length; i++){
            int ptr = i-1;
            if(nums[i]>nums[ptr]) continue;
                //Continue skip the current iteration, while break will terminate the whole loop.
            else{
                while(ptr>=0 && nums[i]<nums[ptr]){
                    ptr--;
                }
                shift(nums, ptr+1, i);
            }
        }
        return nums;
    }

    /**
     * Shift [start, end-1} to right by 1, shift [end] to [start]
     * @param nums
     * @param start
     * @param end
     */
    private void shift(int[] nums, int start, int end){
        int tmp = nums[end];
        for(int i=end; i>start; i--){
            nums[i] = nums[i-1];
        }
        nums[start] = tmp;
    }
}
