package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/maximal-rectangle/
 * Given a rows x cols binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
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
    //第二种解法是借鉴“Largest Rectangle in Histogram”的解法
    //并没有去求得经过某个点的最大的rectangle，而是求底边经过某个点的最高的rectangle的面积的最大值
    //全局的最大值必然在这些长方形之中。
    //下面解法的复杂度：Time O(MN), Space O(MN).
    //因为下面求height[][], left[][], right[][], 以及最后求res的复杂度都是MN，所以总的复杂度也是MN
    public int maximalRectangle(char[][] matrix) {
        if(matrix==null || matrix.length==0 || matrix[0].length==0){
            return 0;
        }

        int m = matrix.length;
        int n = matrix[0].length;

        //height[i][j]表达以(i, j)位置为底的，histogram的高度，或者说是第j列，从i行开始往上的连续1的个数，suffix pattern
        int[][] height = new int[m][n];
        for(int j=0; j<n; j++){
            for(int i=0; i<m; i++){
                height[i][j] = matrix[i][j]=='0' ? 0 : 1 + (i==0 ? 0 : height[i-1][j]);
            }
        }

        //left[i][j]表达第i行，左侧最靠近j位置，第一个比height[i][j]小的，列位置
        int[][] left = new int[m][n];
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                if(j==0){
                    left[i][j] = -1;
                }
                else{
                    int ptr = j - 1;
                    while(ptr>=0 && height[i][ptr]>=height[i][j]){
                        ptr = left[i][ptr];
                    }
                    left[i][j] = ptr;
                }
            }
        }

        //right[i][j]表达第i行，右侧最靠近j位置，第一个比height[i][j]小的，列位置
        int[][] right = new int[m][n];
        for(int i=0; i<m; i++){
            for(int j=n-1; j>=0; j--){
                if(j==n-1){
                    right[i][j] = n;
                }
                else{
                    int ptr = j + 1;
                    while(ptr<n && height[i][ptr]>=height[i][j]){
                        ptr = right[i][ptr];
                    }
                    right[i][j] = ptr;
                }
            }
        }

        int res = 0;
        for(int i=0; i<m; i++){
            for(int j=0; j<n; j++){
                int width = right[i][j] - left[i][j] - 1;
                res = Math.max(res, width * height[i][j]);
            }
        }

        return res;
    }
}
