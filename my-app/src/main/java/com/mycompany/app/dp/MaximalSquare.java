package com.mycompany.app.dp;

/**
 * https://leetcode.com/problems/maximal-square/
 * Given an m x n binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
 *
 * Example 1:
 * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 4
 *
 * Example 2:
 * Input: matrix = [["0","1"],["1","0"]]
 * Output: 1
 *
 * Example 3:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Constraints:
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 300
 * matrix[i][j] is '0' or '1'.
 */

public class MaximalSquare{
    public int maximalSquare(char[][] matrix) {
        //构建的dp子问题： 以[i, j]为右下角的正方形的最大边长
        //dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
        //dp[0][j] = "0 / 1"
        //dp[i][0] = "0 / 1"
        int[][] dp = new int[matrix.length][matrix[0].length];
        int res = 0;
        for(int i=0; i<matrix.length; i++){
            dp[i][0] = matrix[i][0] == '1' ? 1 : 0;
            res = Math.max(res, dp[i][0]);
        }
        for(int j=0; j<matrix[0].length; j++){
            dp[0][j] = matrix[0][j] == '1' ? 1 : 0;
            res = Math.max(res, dp[0][j]);
        }
        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[0].length; j++){
                dp[i][j] = matrix[i][j] == '1' ? Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1 : 0;
                res = Math.max(res, dp[i][j]);
            }
        }
        return res*res;
    }
}