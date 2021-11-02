package com.mycompany.app.priorityQueue;
/**
 * https://leetcode.com/problems/kth-largest-element-in-an-array/
 * Find the kth largest element in an unsorted array.
 * Note that it is the kth largest element in the sorted order, not the kth distinct element.
 * For example,
 * Given [3,2,1,5,6,4] and k = 2, return 5.
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ array's length.
*/

import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Analysis:
 * Typical PriorityQueue problem.
 * Use a minHeap, and maintain the size to be k, then peek will be the Kth largest.
 * Will implement both kth largest and kth smallest.
 */
public class KthLargestElementInAnArray {
    public static void main(String[] args){
        KthLargestElementInAnArray instance = new KthLargestElementInAnArray();
        int[] nums = {4, 1, 2, 4, 6, 10};
        System.out.println(instance.kthLargest(2, nums));
    }
    public int kthLargest(int k, int[] array){
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(k);
        for(int tmp : array){
            if(minHeap.size()<k){
                minHeap.add(tmp);
            }
            else{
                if(minHeap.peek()<tmp){
                    minHeap.poll();
                    minHeap.add(tmp);
                }
            }
            /*
            上面写法可以说是对下面的小优化
            要避免在size==k的时候先poll，再add，错误的！
            minHeap.add(tmp);
            if(minHeap.size()>k){
                minHeap.poll();
            }
             */
        }
        return minHeap.peek();
    }

    public int kthSmallest(int k, int[] array){
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        for(int tmp : array){
            if(maxHeap.size()<k){
                maxHeap.add(tmp);
            }
            else{
                if(maxHeap.peek()>tmp){
                    maxHeap.poll();
                    maxHeap.add(tmp);
                }
            }
        }
        return maxHeap.peek();
    }

    //下面这种是用quickSelect实现的O(n)的算法。
    //关于quickSelect的复杂度分析详见：com\mycompany\app\QuickSelect.java
    public int findKthLargest(int[] nums, int k) {
        int l = nums.length-k+1;
        return quickSelect(nums, l);
    }

    private int quickSelect(int[] nums, int k){
        if(k==0 || k>nums.length){
            return Integer.MIN_VALUE;
        }
        return search(nums, 0, nums.length-1, k);
    }

    //下面这个search function也可以用iteration写，详见"MajorityElement.java"里第二种解法。
    private int search(int[] nums, int left, int right, int k){
        //termination condition
        if(left==right){
            return nums[left];
        }

        int idx = partition(nums, left, right);
        if(idx==k-1){
            return nums[idx];
        }
        else if(idx>k-1){
            return search(nums, left, idx-1, k);
        }
        else{
            return search(nums, idx+1, right, k);
        }
    }

    private int partition(int[] nums, int start, int end){
        int left = start + 1;
        int right = end;
        //这里一定要有等号！是为了保证right严格指向<=的位置，如果nums={1, 2}，开始left && right都指向2，如果没有等号，那么right就不会动，那么会面的swap(nums, start, right)就错了
        while(left<=right){
            while(right>=start && nums[right]>nums[start]){
                right--;
            }
            while(left<=end && nums[left]<=nums[start]){
                left++;
            }
            if(left<right){
                swap(nums, right, left);
            }
        }
        swap(nums, start, right);
        return right;
    }

    private void swap(int[] nums, int ptr1, int ptr2){
        int tmp = nums[ptr1];
        nums[ptr1] = nums[ptr2];
        nums[ptr2] = tmp;
    }

}
