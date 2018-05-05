package com.mycompany.app;

/**
 * Question:
 * Rotate an array of n elements to the right by k steps.
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4].
 * Note:
 * Try to come up as many solutions as you can, there are at least 3 different ways to solve this problem.
 */

/**
 * Analysis:
 * 1. In place solution, is to use a bridge buff between a[0] and a[a.length-1], then shift 1 step at a time.
 * This is a completely in-place solution with O(kn) runtime. One variation over this could be use buf[k] to copy
 * out all tail and then shift k steps in one round. Extra O(k) space trade for O(n+k) runtime.
 *
 * 2. Not in place. If not in place then will have multiple ways.
 * One easy way would be use head and tail pointers.
 * Do pointer manipulation first then dump the data to a new array with new head and tail pointers.
 *
 * 3. The best in place solution is to reverse the whole array and reverse each sub-array.
 * In this way we can do it in O(2n).
 */

public class RotateArray{
    public void rotateArray(int[] nums, int k){
        if(nums==null || nums.length==0) throw new IllegalArgumentException();
        for(int i=0; i<k; i++){
            int tmp = nums[nums.length-1];
            for(int j=nums.length-1; j>0; j--){
                nums[j] = nums[j-1];
            }
            nums[0] = tmp;
        }
        return;
    }

    public void rotateArray2(int[] nums, int k){
        reverseArray(nums, 0, nums.length-1);
        reverseArray(nums, 0, k-1);
        reverseArray(nums, k, nums.length-1);
        return;
    }

    private void reverseArray(int[] nums, int start, int end){
        int tmp;
        while(start<=end){
            tmp = nums[start];
            nums[start] = nums[end];
            nums[end] = tmp;
            start++;
            end--;
        }
        return;
    }




}
