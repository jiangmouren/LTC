package com.mycompany.app;

/**
 * Created by jiangmouren on 6/17/17.
 */

/**
 * Question:
 * Implement QuickSort
 */

import java.util.*;

/**
 * Analysis:
 * QuickSort is the most commonly used sort algorithm.
 * In Java, Arrays.sort() are implemented as QuickSort.
 * Worst Case: O(n^2); Average Case: O(nlgn); Best Case: O(nlgn)
 * Not stable, but in place.
 */
public class QuickSort {
    public static void main(String[] args){
        int[] nums = {4, 1, 2, 4, 6, 10};
        QuickSort instance = new QuickSort();
        instance.quickSort(nums);
        for(int num : nums){
            System.out.println(num);
        }
    }

    public void quickSort(int[] nums){
        if(nums==null) throw new IllegalArgumentException("Inputs cannot be null");
        if(nums.length<2) return;
        quickSortHelper(nums, 0, nums.length-1);
    }

    private void quickSortHelper(int[] nums, int start, int end){
        int pivot = partition(nums, start, end);
        if(pivot-1>start){
            quickSortHelper(nums, start, pivot-1);
        }
        if(pivot+1<end){
            quickSortHelper(nums, pivot+1, end);
        }
    }

    private int partition(int[] nums, int start, int end){
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
        //我们这样做的目的是为了drop pivot位置的那个element，这样能保证每一步我们sub-problem的sized都是在缩小的
        //{4, 1, 2, 4, 6, 10}，比如左边的例子，我们以第一个4为pivot去partition，最后ptr2为停在中间那个4的位置。
        //这个时候，如果我们按照上面"[start, pivot] + [pivot+1, end]"的办法拆分sub-problem，
        //就会发现{4, 1, 2, 4}这部分会陷入死循环。
        swap(nums, start, ptr2);
        return ptr2;
    }

    private void swap(int[] nums, int ptr1, int ptr2){
        int tmp = nums[ptr1];
        nums[ptr1] = nums[ptr2];
        nums[ptr2] = tmp;
    }

    //上面的quickSort也可以写成iterative的。没有办法借鉴merge sort iterative的写法，因为这里没有固定的区间宽度。
    //在merge sort那边可以在外层loop on待sort的区间宽度。
    //这里可以借鉴BFS用一个queue来register待处理的job

    private void quickSortIterative(int[] nums){
        Queue<List<Integer>> jobs = new LinkedList<>();
        List<Integer> job = new ArrayList<>();
        job.add(0);
        job.add(nums.length-1);
        jobs.add(job);
        while(!jobs.isEmpty()){
            List<Integer> cur = jobs.poll();
            int left = cur.get(0);
            int right = cur.get(1);
            int pivot = partition(nums, left, right);
            if(pivot-1>left){
                List<Integer> leftJob = new ArrayList<>();
                leftJob.add(left);
                leftJob.add(pivot-1);
                jobs.add(leftJob);
            }
            if(pivot+1<right){
                List<Integer> rightJob = new ArrayList<>();
                rightJob.add(pivot+1);
                rightJob.add(right);
                jobs.add(rightJob);
            }
        }
    }
}
