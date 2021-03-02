package com.mycompany.app;

/**
 * https://leetcode.com/problems/maximum-product-subarray/
 * Given an integer array nums, find the contiguous subarray within an array (containing at least one number) which has the largest product.
 *
 * Example 1:
 * Input: [2,3,-2,4]
 * Output: 6
 * Explanation: [2,3] has the largest product 6.
 *
 * Example 2:
 * Input: [-2,0,-1]
 * Output: 0
 * Explanation: The result cannot be 2, because [-2,-1] is not a subarray.
 */

public class MaximumProductSubarray {
    public int maxProduct(int[] nums) {
        //这个题很容易犯的错误就是简单套用"Maximum Subarray"的迭代关系
        //suf[i] = max{suf[i-1]*nums[i], nums[i]}其实不再成立了，因为如果nums[i]是负数，而suf[i-1]里面最大的是2，而其实里面有-2，因为小于2被丢掉，但是在这里-2*nums[i]却是更大的数
        //所以新的迭代关系里面需要保留之前的最大正数和最小负数
        //res[i] = max{suf[i], res[i-1]}
        //suf[i] = max{pos[i-1]*nums[i], neg[i-1]*nums[i], nums[i]}
        //pos[i] = max{nums[i], pos[i-1]*nums[i], neg[i-1]*nums[i]}
        //neg[i] = min{nums[i], pos[i-1]*nums[i], neg[i-1]*nums[i]}
        //res[0] = nums[0]
        //pos[0] = max{nums[0], 0}
        //neg[0] = min{nums[0], 0}
        int res = nums[0];
        int pos = Math.max(nums[0], 0);
        int neg = Math.min(nums[0], 0);
        for(int i=1; i<nums.length; i++){
            int posNxt = Math.max(Math.max(nums[i], pos*nums[i]), neg*nums[i]);
            int negNxt = Math.min(Math.min(nums[i], pos*nums[i]), neg*nums[i]);
            int suf = Math.max(Math.max(pos*nums[i], neg*nums[i]), nums[i]);
            int resNxt = Math.max(suf, res);
            res = resNxt;
            pos = posNxt;
            neg = negNxt;
        }
        return res;
    }
}
