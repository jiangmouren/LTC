package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/maximal-rectangle/
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 *
 *
 *
 * Example 1:
 *            1 0 1 0 0
 *            1 0 1 1 1
 *            1 1 1 1 1
 *            1 0 0 1 0
 * Input: matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]
 * Output: 6
 * Explanation: The maximal rectangle is shown in the above picture.
 *
 * Example 2:
 * Input: matrix = []
 * Output: 0
 *
 * Example 3:
 * Input: matrix = [["0"]]
 * Output: 0
 *
 * Example 4:
 * Input: matrix = [["1"]]
 * Output: 1
 *
 * Example 5:
 * Input: matrix = [["0","0"]]
 * Output: 0
 *
 * Constraints:
 * rows == matrix.length
 * cols == matrix.length
 * 0 <= row, cols <= 200
 * matrix[i][j] is '0' or '1'.
 */

public class MaximalRectangle{
    //第一种解法的思路是借鉴“maximalSquare”，分两步想这个问题
    //每一行来开以d[j]结尾的连续为1的最大长度是可以O(N)求出来的
    //那么以d[i][j]为右下角的最大的矩形的面积，就可以从第i行往上看，从i-k行到i行，最大的矩形面积：min{d[i][j],...,d[i-k][j]}*(k+1)
    //k从0到i，取当中的最大值，就是以dp[i][j]为右下角的最大的矩形面积
    //这里dp[i][j]表达的是第i行，以j结尾的连续为1的最大长度
    public int maximalRectangle(char[][] matrix) {
        if(matrix==null || matrix.length==0){
            return 0;
        }
        int[][] dp = new int[matrix.length][matrix[0].length];
        int res = 0;

        for(int i=0; i<dp.length; i++){
            for(int j=0; j<dp[0].length; j++){
                if(j==0){
                    dp[i][j] = matrix[i][j]=='1' ? 1 : 0;
                }
                else{
                    dp[i][j] = matrix[i][j]=='1' ? dp[i][j-1]+1 : 0;
                }
                int minWidth = Integer.MAX_VALUE;
                for(int k=0; k<=i; k++){
                    minWidth = Math.min(dp[i-k][j], minWidth);
                    int area = minWidth * (k+1);
                    res = Math.max(res, area);
                }
            }
        }
        return res;
    }

    //第二种解法是借鉴“Largest Rectangle in Histogram”的解法
    //并没有去求得经过某个点的最大的rectangle，而是去底边经过某个点的最高的rectangle的面积的最大值
    //全局的最大值必然在这些长方形之中。
    //至于说求面积的方法，既可以用下面的方法，也可以用rectangleInHistogram里面的做法。
    public int maximalRectangleSln2(char[][] matrix) {
        if(matrix==null || matrix.length==0){
            return 0;
        }
        int m = matrix[0].length;
        int[] left = new int[m];
        int[] right = new int[m];
        Arrays.fill(right, m); // initialize right as the rightmost boundary possible
        int[] height = new int[m];
        int res = 0;
        for(int i=0; i<matrix.length; i++){
            int curLeft = 0;
            int curRight = m;//curRight的初值要从m而不是m-1开始，方便后面计算面积
            //后面的重点就是理解每次matrix[i][j]==0的时候，要同步的reset height[j], left[j], right[j]
            // update height
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1')
                    height[j]++;
                else
                    height[j] = 0;//出现0就要reset height
            }
            // update left
            for (int j = 0; j < m; j++) {
                if (matrix[i][j] == '1')
                    left[j] = Math.max(left[j], curLeft);
                else {
                    left[j] = 0;//出现0要reset left bound到curLeft初值
                    curLeft = j + 1;
                }
            }
            // update right
            for (int j = m - 1; j >= 0; j--) {
                if (matrix[i][j] == '1')
                    right[j] = Math.min(right[j], curRight);
                else {
                    right[j] = m;//出现0要reset right bound到curRight初值
                    curRight = j;
                }
            }
            for(int j=0; j<m; j++){
                res = Math.max(res, (right[j]-left[j])*height[j]);
            }
        }

        return res;
    }
}
