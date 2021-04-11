package com.mycompany.app;
/**
 * https://leetcode.com/problems/house-robber/
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed, the only constraint stopping you from robbing
 * each of them is that adjacent houses have security system connected and
 * it will automatically contact the police if two adjacent houses were broken into on the same night.
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 *
 * Example 1:
 * Input: nums = [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 *              Total amount you can rob = 1 + 3 = 4.
 * Example 2:
 * Input: nums = [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 *              Total amount you can rob = 2 + 9 + 1 = 12.
 *
 * Constraints:
 * 0 <= nums.length <= 100
 * 0 <= nums[i] <= 400
 */

/**
 * Analysis:
 * Typical DP problem. Like climbing stairs, like buy and sell stock, you have a consecutive sequence of decisions
 * to make. And the decision at every step, is constrained by what happened before.
 * The approach for this kind of problem is to lay out all possibilities for one step and then back track the recursion following
 * the constraints requirements.
 * For this specific problem:
 * f(n) = Max{A(n), B(n)}, where A(n) is max profit if house(n) robbed, B(n) is max profit if house(n) not robbed.
 * A(n) = B(n-1) + M(n), where M(n) is the amount of money in house(n), n>=1;
 * B(n) = Max{A(n-1), B(n-1)}, n>=1;
 * A(0) = M(0), B(0) = 0;
 *
 * A(1) = B(0) + M(0) = M(0), valid;
 * B(1) = Max{A(0), B(0)} = M(0), valid;
 * So we can start recursion from n==1.
 */

public class HouseRobber{
    public int rob(int[] nums) {
        //dp[n-1] = max{a[n-1]+dp[n-3], dp[n-2]}
        //initial values: dp[0] = a[0], dp[1] = max{a[0], a[1]}
        if(nums==null || nums.length==0){
            return 0;
        }
        if(nums.length==1){
            return nums[0];
        }
        if(nums.length==2){
            return Math.max(nums[0], nums[1]);
        }

        int a = nums[0];
        int b = Math.max(nums[0], nums[1]);
        int c = 0;
        for(int i=2; i<nums.length; i++){
            c = Math.max(nums[i]+a, b);
            a = b;
            b = c;
        }
        return c;
    }
}
