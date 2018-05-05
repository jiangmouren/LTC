package com.mycompany.app;

/**
 * Question:
 * You are given an n x n 2D matrix representing an image.
 * Rotate the image by 90 degrees (clockwise).
 * Follow up:
 * Could you do this in-place?
 */

/**
 * Analysis:
 * Matrix rotation related issues can be resolved by using a combination of horizontal, vertical and diagonal fold.
 * For this specific one, by clockwise rotate 90 degree:
 * The 1st row will become the right most column;
 * The second row will become the second from the right column,
 * ...
 * etc.
 *
 * This sounds familiar:
 * 1. If you fold the matrix by the main diagonal, the result will be 1st row become the left most column,
 * the 2nd row become the 2nd left most column.
 * So basically, if you further reverse the matrix horizontally, we get the result.
 * 2. Or if you fold the matrix by the secondary diagonal, the result will be 1st row become the right most column,
 * the 2nd row become the 2nd right most column.
 * But with the matrix upside down, need to vertically reverse it.
 *
 */

public class RotateImage {
    public void rotate1(int[][] matrix){
        if(matrix==null || matrix.length<=1) return;
        //swap along main diagonal
        for(int i=0; i<matrix.length; i++){
            //Attention: must start as j==i, either wise double reverse, functionally will be wrong.
            for(int j=i; j<matrix.length; j++){
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
        //reverse horizontally
        for(int i=0; i<matrix.length; i++){
            //for(int j=0; j<matrix.length; i++){
            //    int tmp = matrix[i][j];
            //    matrix[i][j] = matrix[i][matrix.length-1-j];
            //    matrix[i][matrix.length-1-j] = tmp;
            //}
            //When you do array reverse has to be 2 pointers.
            int head = 0, tail = matrix.length-1;
            while(head<=tail){
                int tmp = matrix[i][head];
                matrix[i][head] = matrix[i][tail];
                matrix[i][tail] = tmp;
                head++;
                tail--;
            }
        }
        return;
    }

}
