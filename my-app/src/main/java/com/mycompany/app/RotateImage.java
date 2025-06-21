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
 * 这个主要是要In place，否则很简单，关于matrix rotation的详细分析：
 * src\main\resources\Matrix Rotation.docx
 */

public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        //fold by y axis
        for(int i=0; i<n/2; i++){
            for(int j=0; j<n; j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-1-i][j];
                matrix[n-1-i][j] = temp;
            }
        }

        //fold by main diagnal
        for(int i=0; i<n; i++){
            for(int j=i+1; j<n; j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    public void rotateSln2(int[][] matrix) {
        int n = matrix.length;
        for(int layer = 0; layer < n/2; layer++){
            int first = layer;
            int last = n - layer - 1;//last = first + lengh = layer + (n-2*layer-1)=n-layer-1
            for(int i = first; i < last; i++){
                int offset = i - first;
                int top = matrix[first][i];//buffer top entry
                matrix[first][i] = matrix[last - offset][first];//left to top
                matrix[last - offset][first] = matrix[last][last - offset];//bottom to left
                matrix[last][last - offset] = matrix[i][last];//right to bottom
                matrix[i][last] = top;//top to right
            }
        }
        return;
    }

}
