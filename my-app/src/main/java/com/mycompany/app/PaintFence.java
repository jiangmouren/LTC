package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/paint-fence/
 * You are painting a fence of n posts with k different colors.
 * You must paint the posts following these rules:
 * Every post must be painted exactly one color.
 * At most one pair of adjacent fence posts can have the same color.
 * Given the two integers n and k, return the number of ways you can paint the fence.
 *
 * Example 1:
 * Input: n = 3, k = 2
 * Output: 6
 * Explanation: All the possibilities are shown.
 * Note that painting all the posts red or all the posts green is invalid because
 * there can only be at most one pair of adjacent posts that are the same color.
 *
 * Example 2:
 * Input: n = 1, k = 1
 * Output: 1
 *
 * Example 3:
 * Input: n = 7, k = 2
 * Output: 42
 *
 * Constraints:
 * 1 <= n <= 50
 * 1 <= k <= 105
 * The answer is guaranteed to be in the range [0, 231 - 1] for the given n and k.
 */

public class PaintFence {
    public int numWays(int n, int k) {
        //这个题目的描述非常不清楚，让人误以为最多只能有两2个fence有相同的颜色，其实想说的是不能有连续超过2个有相同的颜色
        //dp构造的方式，是根据末尾两个颜色是否相同来拆分问题：
        //如果n&n-1颜色不同，那么有(k-1)*dp[n-1]种涂法
        //如果n&n-1颜色相同，那么他们必然与n-2的颜色不同，所以一共有(k-1)*dp[n-2]种涂法

        int[] dp = new int[n];
        dp[0] = k;
        if(n>1){
            dp[1] = k*k;
        }

        for(int i = 2; i < n; i++){
            dp[i] = (k-1) * dp[i-1] + (k-1) * dp[i-2];
        }
        return dp[n-1];
    }
}
