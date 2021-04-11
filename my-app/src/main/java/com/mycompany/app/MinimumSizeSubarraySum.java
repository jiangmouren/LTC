package com.mycompany.app;

/**
 * https://leetcode.com/problems/minimum-size-subarray-sum/
 * Given an array of positive integers nums and a positive integer target,
 * return the minimal length of a contiguous subarray [numsl, numsl+1, ..., numsr-1, numsr]
 * of which the sum is greater than or equal to target. If there is no such subarray, return 0 instead.
 *
 * Example 1:
 * Input: target = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: The subarray [4,3] has the minimal length under the problem constraint.
 *
 * Example 2:
 * Input: target = 4, nums = [1,4,4]
 * Output: 1
 *
 * Example 3:
 * Input: target = 11, nums = [1,1,1,1,1,1,1,1]
 * Output: 0
 *
 * Constraints:
 * 1 <= target <= 109
 * 1 <= nums.length <= 105
 * 1 <= nums[i] <= 105
 */
public class MinimumSizeSubarraySum{
    public int minSubArrayLen(int target, int[] nums) {
        //用两个Pointer，做一个sliding window
        int ptr0 = 0;
        int ptr1 = 0;
        int sum = 0;
        int minL = Integer.MAX_VALUE;
        int n = nums.length;
        while(ptr0<n){
            while(sum<target && ptr1<n){
                sum += nums[ptr1];
                ptr1++;
            }
            if(sum>=target){
                minL = Math.min(minL, ptr1-ptr0);
            }
            else if(ptr1>=n){
                break;
            }

            sum -= nums[ptr0];
            ptr0++;
        }
        return minL==Integer.MAX_VALUE ? 0 : minL;
    }
}