package com.mycompany.app;
/**
 * Question:
 * Write an efficient algorithm that searches for a value in an m x n matrix.
 * This matrix has the following properties:
 * Integers in each row are sorted in ascending from left to right.
 * Integers in each column are sorted in ascending from top to bottom.
 * For example,
 * Consider the following matrix:
 * [
 *   [1,   4,  7, 11, 15],
 *   [2,   5,  8, 12, 19],
 *   [3,   6,  9, 16, 22],
 *   [10, 13, 14, 23, 24],
 *   [18, 21, 23, 26, 30]
 * ]
 * Given target = 5, return true.
 * Given target = 20, return false.
 * TODO:
 */

/**
 * Analysis:
 * This is really just to test how good you know Binary Search.
 * Another question is how to sort a matrix into something like this?
 * Wiggle Sort!!!
 * If you merge this rows, you will find it is following Wiggle Sort Order.
 *
 * The way we Binary Search this matrix is like following:
 * Take turns to BS rows and columns until there is only 1 row or column left behind.
 * For example, searching for 20.
 * 1. In round 1, we can get rid of Row-1(starting from 1), Row-2;
 * 2. In round 2, we can get rid of Column-1, Column-5;
 * 3. In round 3, we can get rid of Row-1, Row-3;
 * 4. In last round, BS the last row, we cannot find it.
 *
 * I need to prove why by the end we will have only 1 row or column left behind.
 * The way it works, once we finish the row BS, the left most column will be all less then target,
 * while the right most column will be all larger than the target.
 * So at the column BS turn, we can chop off those columns.
 *
 */

public class SearchA2DMatrixII{
    public boolean find(int target, int[][] matrix){
        int[] up = {0};
        int[] down = {matrix.length-1};
        int[] left = {0};
        int[] right = {matrix[0].length};
        boolean checkRow = (matrix.length < matrix[0].length);
        while(up[0]!=down[0] && left[0]!=right[0]){
            if(checkRow){
                if(checkRow(target, matrix, up, down, left, right)){
                    return true;
                }
                checkRow = !checkRow;
            }
            else{
                if(checkColumn(target, matrix, up, down, left, right)){
                    return true;
                }
                checkRow = !checkRow;
            }
        }
        return binarySearch(target, matrix, up, down, left, right);
    }

    private boolean checkRow(int target, int[][] matrix, int[] up, int[] down, int[] left, int[] right){
        for(int i=up[0]; i<=down[0]; i++){
            if(matrix[i][left[0]]==target || matrix[i][right[0]]==target){
                return true;
            }
            else if(matrix[i][left[0]]>target){
                down[0] = i-1;//will exit and return false;
            }
            else if(matrix[i][right[0]]<target){
                up[0] = i+1;
            }
        }
        return false;
    }

    private boolean checkColumn(int target, int[][] matrix, int[] up, int[] down, int[] left, int[] right){
        for(int i=left[0]; i<=right[0]; i++){
            if(matrix[up[0]][i]==target || matrix[down[0]][i]==target){
                return true;
            }
            else if(matrix[up[0]][i]>target){
                right[0] = i-1;//will exit and return false;
            }
            else if(matrix[down[0]][i]<target){
                left[0] = i+1;
            }
        }
        return false;
    }

    private boolean binarySearch(int target, int[][] matrix, int[] up, int[] down, int[] left, int[] right){
        if(up[0]==down[0]){//binary search row
            while(left[0]<matrix[0].length && right[0]>0 && left[0]<=right[0]){
                int mid = (left[0]+right[0])/2;
                if(matrix[up[0]][mid]==target) return true;
                else if(matrix[up[0]][mid]>target){
                    right[0] = mid-1;
                }
                else{
                    left[0] = mid+1;
                }
            }
            return false;
        }
        else{//binary search column
            while(up[0]<matrix.length && down[0]>0 && up[0]<=down[0]){
                int mid = (up[0]+down[0])/2;
                if(matrix[mid][left[0]]==target) return true;
                else if(matrix[mid][left[0]]>target){
                    down[0] = mid-1;
                }
                else{
                    up[0] = mid+1;
                }
            }
            return false;
        }
    }
}
