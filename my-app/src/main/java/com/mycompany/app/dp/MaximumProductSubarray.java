package com.mycompany.app.dp;

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
    /**
     * 用dp[i]表达以i结尾的最大的product, 然后用个max来keep track of running max
     */
    public int maxProduct(int[] nums) {
        //这个题很容易犯的错误就是简单套用"Maximum Subarray"的迭代关系
        //suf[i] = max{suf[i-1]*nums[i], nums[i]}其实不再成立了，因为如果nums[i]是负数，而suf[i-1]里面最大的是2，而其实里面有-2，因为小于2被丢掉，但是在这里-2*nums[i]却是更大的数
        //所以新的迭代关系里面需要保留之前的最大正数和最小负数, 这里suf指的是都要包含最后一个的意思，但实际上我们求解sub-problem的过程却是一个个prefix解过来的.
        int dp = nums[0];
        int pos = Math.max(dp, 0);//永远大于等于0
        int neg = Math.min(dp, 0);//永远小于等于0
        int max = dp;
        for(int i=1; i<nums.length; i++){
            int posNxt = Math.max(Math.max(nums[i]*pos, nums[i]*neg), nums[i]);
            int negNxt = Math.min(Math.min(nums[i]*pos, nums[i]*neg), nums[i]);
            int dpNxt = Math.max(posNxt, nums[i]);
            pos = posNxt;
            neg = negNxt;
            dp = dpNxt;
            max = Math.max(max, dp);
        }
        return max;
    }
}
