package com.mycompany.app;

/**
 * https://leetcode.com/problems/diagonal-traverse/
 * Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.
 *
 * Example 1:
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [1,2,4,7,5,3,6,8,9]
 *
 * Example 2:
 * Input: mat = [[1,2],[3,4]]
 * Output: [1,2,3,4]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 104
 * 1 <= m * n <= 104
 * -105 <= mat[i][j] <= 105
 */

//解这个题目的核心有两个：
//1. 搞清左下往右上，右上往左下的(i,j)的变化pattern
//2. 搞清起始点的pattern，搞清起始点的pattern的核心是允许matrix的扩展，接受虚拟cell
public class DiagonalTraverse {
    //在sln2的基础上的改进，直接跳过虚拟cell，从valid cell开始
    public int[] findDiagonalOrder(int[][] mat) {
        boolean up = true;
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m*n];
        int iStart = 0;
        int jStart = 1;
        int ptr = 0;
        while(ptr<m*n){
            if(up){
                //adjust the starting position, instead of traversing through invalid entries
                int iAdj = iStart;
                int jAdj = 0;
                if(iStart>=m){
                    iAdj = m-1;
                    jAdj = iStart - (m-1);
                }
                for(int i=iAdj, j=jAdj; i>=0 && j<n; i--, j++){
                    res[ptr] = mat[i][j];
                    ptr++;
                }
                iStart += 2;
            }
            else{
                int iAdj = 0;
                int jAdj = jStart;
                if(jAdj>=n){
                    iAdj = jStart-(n-1);
                    jAdj = n-1;
                }
                for(int j=jAdj, i=iAdj; j>=0 && i<m; j--, i++){
                    res[ptr] = mat[i][j];
                    ptr++;
                }
                jStart += 2;
            }
            up = !up;
        }
        return res;
    }

    //以下是我开始的解，逐个traverse虚拟cell，直到valid cell才开始take values
    //traverse虚拟cell花费大量时间
    public int[] findDiagonalOrderSln2(int[][] mat) {
        boolean up = true;
        int m = mat.length;
        int n = mat[0].length;
        int[] res = new int[m*n];
        int iStart = 0;
        int jStart = 1;
        int ptr = 0;
        while(ptr<m*n){
            if(up){
                for(int i=iStart, j=0; i>=0 && j<n; i--, j++){
                    if(i<m){
                        res[ptr] = mat[i][j];
                        ptr++;
                    }
                }
                iStart += 2;
            }
            else{
                for(int j=jStart, i=0; j>=0 && i<m; j--, i++){
                    if(j<n){
                        res[ptr] = mat[i][j];
                        ptr++;
                    }
                }
                jStart += 2;
            }
            up = !up;
        }
        return res;
    }
}
