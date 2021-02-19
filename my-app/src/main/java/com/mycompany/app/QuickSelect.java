package com.mycompany.app;

/**
 * quickSelect的复杂度：
 * Best Case: O(n)
 * Average Case: O(n)
 * Worst Case: O(n^2)
 * Complexity Analysis: src\main\resources\QuickSortQuickSelectComplexity.PNG
 */
public class QuickSelect {
    public static void main(String[] args){
        QuickSelect instance = new QuickSelect();
        int[] nums = {4, 1, 2, 4, 6, 10};
        System.out.println(instance.quickSelect(nums, 1));
        System.out.println(instance.quickSelect(nums, 2));
        System.out.println(instance.quickSelect(nums, 3));
        System.out.println(instance.quickSelect(nums, 4));
        System.out.println(instance.quickSelect(nums, 5));
        System.out.println(instance.quickSelect(nums, 6));
        System.out.println(instance.quickSelect(nums, 7));
    }

    public int quickSelect(int[] nums, int k){
        if(k==0 || k>nums.length){
            return Integer.MIN_VALUE;
        }
        return search(nums, 0, nums.length-1, k);
    }

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
