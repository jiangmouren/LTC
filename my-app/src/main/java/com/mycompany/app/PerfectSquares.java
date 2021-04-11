package com.mycompany.app;

/**
 * https://leetcode.com/problems/perfect-squares/
 * Given an integer n, return the least number of perfect square numbers that sum to n.
 * A perfect square is an integer that is the square of an integer;
 * in other words, it is the product of some integer with itself.
 * For example, 1, 4, 9, and 16 are perfect squares while 3 and 11 are not.
 *
 * Example 1:
 * Input: n = 12
 * Output: 3
 * Explanation: 12 = 4 + 4 + 4.
 *
 * Example 2:
 * Input: n = 13
 * Output: 2
 * Explanation: 13 = 4 + 9.
 *
 * Constraints:
 * 1 <= n <= 104
 */
public class PerfectSquares{
    //就是用很传统的办法构造dp解决，然后总的步数是n步，每一步的选项粗略的可以估计成sqrt(n)
    //所以总的复杂度就是O(n^(1.5))
    public int numSquares(int n) {
        int[] dp = new int[n+1];
        for(int i=2; i<=n; i++){
            dp[i] = n;
        }

        dp[1] = 1;
        for(int i=2; i<=n; i++){
            int max = (int)Math.sqrt(i);
            while(max>0){
                dp[i] = Math.min(dp[i], dp[i-max*max]);
                max--;
            }
            dp[i]++;
        }
        return dp[n];
    }
}