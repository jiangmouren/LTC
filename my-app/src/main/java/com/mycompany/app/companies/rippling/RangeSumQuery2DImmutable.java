package com.mycompany.app.companies.rippling;

/**
 * https://leetcode.com/problems/range-sum-query-2d-immutable/
 * Given a 2D matrix matrix, handle multiple queries of the following type:
 *
 * Calculate the sum of the elements of matrix inside the rectangle defined by
 * its upper left corner (row1, col1) and lower right corner (row2, col2).
 * Implement the NumMatrix class:
 *
 * NumMatrix(int[][] matrix) Initializes the object with the integer matrix matrix.
 * int sumRegion(int row1, int col1, int row2, int col2)
 * Returns the sum of the elements of matrix inside the rectangle
 * defined by its upper left corner (row1, col1) and lower right corner (row2, col2).
 * You must design an algorithm where sumRegion works on O(1) time complexity.
 */
//要O(1)求面试，说明开始一定是cache了一些面积
//题目的核心就是搞清楚如何去cache面积，一个比较巧妙的dp问题
public class RangeSumQuery2DImmutable {
    int[][] dp;

    public RangeSumQuery2DImmutable(int[][] matrix) {
        //dp[i][j] = matrix[i][j] + dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1];
        this.dp = new int[matrix.length][matrix[0].length];
        //set up initial values
        for(int i=0; i<matrix[0].length; i++){
            dp[0][i] = matrix[0][i];
            if(i>0){
                dp[0][i] += dp[0][i-1];
            }
        }
        for(int i=1; i<matrix.length; i++){
            dp[i][0] = matrix[i][0] + dp[i-1][0];
        }

        for(int i=1; i<matrix.length; i++){
            for(int j=1; j<matrix[0].length; j++){
                dp[i][j] = matrix[i][j] + dp[i-1][j] + dp[i][j-1] - dp[i-1][j-1];
            }
        }
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int left = 0;
        int top = 0;
        int topLeft = 0;
        if(col1>0){
            left = dp[row2][col1-1];
        }
        if(row1>0){
            top = dp[row1-1][col2];
        }
        if(row1>0 && col1>0){
            topLeft = dp[row1-1][col1-1];
        }
        return dp[row2][col2] - left - top + topLeft;
    }
}
