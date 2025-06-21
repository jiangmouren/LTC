package com.mycompany.app.matrixTraversal;

import java.util.*;

/**
 * https://leetcode.com/problems/spiral-matrix/
 * Given an m x n matrix, return all elements of the matrix in spiral order.
 *
 * Example 1:
 * Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,3,6,9,8,7,4,5]
 *
 * Example 2:
 * Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 * Constraints:
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m, n <= 10
 * -100 <= matrix[i][j] <= 100
 */
public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        int w = matrix[0].length;
        int h = matrix.length;
        int l = w;
        int cnt = 0;
        int[] dir = getDir(cnt);
        int ptr = 0;
        int[] pos = {0, 0};
        while(ptr<l){
            res.add(matrix[pos[0]][pos[1]]);
            if(ptr==l-1){
                cnt++;
                dir = getDir(cnt);
                ptr = 0;
                l = l==w ? --h : --w;
            }
            else{
                ptr++;
            }
            pos[0] += dir[0];
            pos[1] += dir[1];
        }
        return res;
    }

    private int[] getDir(int cnt){
        int[][] dircs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int idx = cnt%4;
        return dircs[idx];
    }
}
