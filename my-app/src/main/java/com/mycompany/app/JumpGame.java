package com.mycompany.app;

/**
 * https://leetcode.com/problems/jump-game-ii/
 * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
 * Each element in the array represents your maximum jump length at that position.
 * Your goal is to reach the last index in the minimum number of jumps.
 * You can assume that you can always reach the last index.
 *
 * Example 1:
 * Input: nums = [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 * Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Example 2:
 * Input: nums = [2,3,0,1,4]
 * Output: 2
 *
 * Constraints:
 * 1 <= nums.length <= 1000
 * 0 <= nums[i] <= 105
 */

public class JumpGame{
    public int jump(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        dp[n-1] = 0;
        for(int i=n-2; i>=0; i--){
            dp[i] = Integer.MAX_VALUE;
            for(int j=1; j<=nums[i]; j++){
                if(i+j<n){
                    dp[i] = Math.min(dp[i], dp[i+j]);
                }
            }
            dp[i] = dp[i]!=Integer.MAX_VALUE ? dp[i]+1 : dp[i];//当中有一些点可能调不到尾部，就保留Max_Value
        }
        return dp[0];
    }
}