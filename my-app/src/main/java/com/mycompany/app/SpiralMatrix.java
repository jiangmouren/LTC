package com.mycompany.app;

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
        int width = matrix[0].length-1;
        int height = matrix.length-1;
        int cnt = 0;
        int[] pos = {0, 0};
        List<Integer> res = new ArrayList<>();
        res.add(matrix[pos[0]][pos[1]]);

        while(width>0 || height>0){
            int[] dirc = getNext(cnt);
            if(dirc[0]==0){//moving horizontally
                for(int i=0; i<width; i++){
                    pos[1] += dirc[1];
                    res.add(matrix[pos[0]][pos[1]]);
                }
                if(height==0){//这是避免回头的情况，就是height已经是0，但是width还没减到0，那么还会回来横向反方向走一次
                    break;
                }
                if(cnt>0){//第一次不减，后面每次都要减
                    width--;
                }
                cnt++;
            }
            else{
                for(int i=0; i<height; i++){
                    pos[0] += dirc[0];
                    res.add(matrix[pos[0]][pos[1]]);
                }
                if(width==0){
                    break;
                }
                if(cnt>0){
                    height--;
                }
                cnt++;
            }
        }
        return res;
    }

    private int[] getNext(int cnt){
        int[][] dircs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int idx = cnt%4;
        return dircs[idx];
    }
}
