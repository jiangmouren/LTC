package com.mycompany.app;

/**
 * Question:
 * https://leetcode.com/problems/maximum-subarray/
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
 *
 * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
 *
 * Example 1:
 * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 *
 * Example 2:
 * Input: nums = [1]
 * Output: 1
 *
 * Example 3:
 * Input: nums = [0]
 * Output: 0
 *
 * Example 4:
 * Input: nums = [-1]
 * Output: -1
 *
 * Example 5:
 * Input: nums = [-2147483647]
 * Output: -2147483647
 *
 * Constraints:
 * 1 <= nums.length <= 2 * 104
 * -231 <= nums[i] <= 231 - 1
 */

public class MaximumSubarray {
    //resultMax[i] = Math.max(resultMax[i-1], suffixMax[i]);
    //suffixMax[i] = Math.max(nums[i], suffixMax[i-1]+nums[i]);
    //suffixMax[0] = nums[0];
    //resultMax[0] = nums[0];
    public int maxSubArray(int[] nums) {
        int[] suffixMax = new int[nums.length];
        int[] resultMax = new int[nums.length];
        suffixMax[0] = nums[0];
        resultMax[0] = nums[0];
        for(int i=1; i<nums.length; i++){
            suffixMax[i] = Math.max(nums[i], suffixMax[i-1]+nums[i]);
            resultMax[i] = Math.max(resultMax[i-1], suffixMax[i]);
        }
        return resultMax[nums.length-1];
    }

    //对上面的解法，常见的优化就是下面这种省去dp的array，而改成反复使用中间变量
    public int maxmumSubarray(int[] nums){
        if(nums==null || nums.length==0) throw new IllegalArgumentException("Input cannot be null or empty");
        int sub_max=nums[0], suffix_max=nums[0];
        int sub_max_next, suffix_max_next;
        for(int i=1; i<nums.length; i++){
            suffix_max_next = Math.max(suffix_max+nums[i], nums[i]);
            sub_max_next = Math.max(sub_max, suffix_max_next);
            sub_max = sub_max_next;
            suffix_max = suffix_max_next;
        }
        return sub_max;
    }
}
