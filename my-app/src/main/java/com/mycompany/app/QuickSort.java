package com.mycompany.app;

/**
 * Created by jiangmouren on 6/17/17.
 */

/**
 * Question:
 * Implement QuickSort
 */

/**
 * Analysis:
 * QuickSort is the most commonly used sort algorithm.
 * In Java, Arrays.sort() are implemented as QuickSort.
 * Worst Case: O(n^2); Average Case: O(nlgn); Best Case: O(nlgn)
 * Not stable, but in place.
 */
public class QuickSort {
    public int[] quickSort(int[] nums){
        if(nums==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(nums.length<2) return nums;
        quickSortHelper(nums, 0, nums.length-1);
        return nums;
    }

    //The pivot function cannot be recursive, have to use this wrapper function.
    private void quickSortHelper(int[] nums, int start, int end){
        int pivot = pivot(nums, start, end);
        if(pivot-1>start){
            quickSortHelper(nums, start, pivot-1);
        }
        if(pivot+1<end){
            quickSortHelper(nums, pivot+1, end);
        }
    }

    private int pivot(int[] nums, int start, int end){
        //take nums[start] as the pivot point.
        int ptr1 = start+1, ptr2 = end;
        while(ptr1<=ptr2){
            //Because, right >, left <=, ptr1 and ptr2 will never stop at the same point
            //Common mistake is to miss the boundary check, or not putting it as the first check.
            while(ptr2>=start && nums[ptr2]>nums[start]){
                //Because I am using ">", ptr2 will never go beyond start
                ptr2--;
            }
            while(ptr1<=end && nums[ptr1]<=nums[start]){
                //left side will take the equal case, consistent with the last swap.
                //Shouldn't matter, but just cleaner to keep it this way.
                ptr1++;
            }
            if(ptr1<ptr2){
                swap(nums, ptr1, ptr2);
            }
        }
        //in the end ptr2 will point to the right most "<=" element
        //We need to do the last swap because of the way we split sub-problems.
        //If we split it as [start, pivot] and [pivot+1, end], then last swap not needed.
        swap(nums, start, ptr2);
        return ptr2;
    }

    private void swap(int[] nums, int ptr1, int ptr2){
        int tmp = nums[ptr1];
        nums[ptr1] = nums[ptr2];
        nums[ptr2] = tmp;
    }
}
