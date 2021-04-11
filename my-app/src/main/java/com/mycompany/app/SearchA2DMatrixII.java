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
 */

public class SearchA2DMatrixII{
    /**
     * 下面这种解法，利用了"左下角"或者"右上角"刚好是行列对折数值的重点，就可以做一个类似于binary search的操作。
     * 这样写不是向右，就是向上，那么时间复杂度就是O(m+n).
     */
    public boolean searchMatrix(int[][] matrix, int target){
        // start our "pointer" in the bottom-left
        int row = matrix.length-1;
        int col = 0;

        while (row >= 0 && col < matrix[0].length) {
            if (matrix[row][col] > target) {
                row--;
            } else if (matrix[row][col] < target) {
                col++;
            } else { // found it
                return true;
            }
        }

        return false;
    }

    /**
     * 底下这种是基于Binary Search的,这种写法的复杂度介于O(lgn)和O(n)之间, Best case是前者，worst case是后者
     * 实际在leetcode上跑出的结果，也是跟上面的解法差不多，速度还略优于上面解法，5ms对上面的6ms.
     * 复杂度分析：
     * src\main\resources\Search2DMatrixII.jpg
     */
    public boolean searchMatrixBinarySearch(int[][] matrix, int target) {
        return search(matrix, target, 0, matrix[0].length-1, matrix.length-1, 0);
    }

    //bottom is when row idx is small; top is when row idx is large
    private boolean search(int[][] matrix, int target, int left, int right, int top, int bottom){
        //termination
        if(left>right || bottom>top){
            return false;
        }
        if(left==right){
            return binarySearchCol(matrix, target, left, bottom, top);
        }
        if(top==bottom){
            //System.out.println("BinarySearchRow: "+"row: "+top+" left: "+left+" right: "+right);
            return binarySearchRow(matrix, target, top, left, right);
        }

        int midRow = (top+bottom)/2;
        if(target<matrix[midRow][left]){//要用left，而不是0
            return search(matrix, target, left, right, midRow-1, bottom);
        }
        if(target>matrix[midRow][right]){//要用right而不是length-1
            //System.out.println("take bottom half & "+"bottom: "+(midRow+1)+" top: "+top);
            return search(matrix, target, left, right, top, midRow+1);
        }

        int midCol = (left+right)/2;
        if(target<matrix[bottom][midCol]){
            //System.out.println("take left half & "+"right: "+(midCol-1));
            return search(matrix, target, left, midCol-1, top, bottom);
        }
        if(target>matrix[top][midCol]){
            return search(matrix, target, midCol+1, right, top, bottom);
        }

        if(right-left>top-bottom){
            return search(matrix, target, left, midCol, top, bottom) || search(matrix, target, midCol+1, right, top, bottom);
        }
        else{
            //System.out.println("Split by row");
            //System.out.println("left: "+left+" right: "+right+" bottom: "+bottom+" top: "+top+" mid: "+ midRow);
            return search(matrix, target, left, right, midRow, bottom) || search(matrix, target, left, right, top, midRow+1);
        }
    }

    private boolean binarySearchCol(int[][] matrix, int target, int col, int bottom, int top){
        boolean found = false;
        while(bottom<=top){
            int mid = (bottom+top)/2;
            if(matrix[mid][col]==target){
                found = true;
                break;
            }
            else if(matrix[mid][col]<target){
                bottom = mid + 1;
            }
            else{
                top = mid - 1;
            }
        }
        return found;
    }

    private boolean binarySearchRow(int[][] matrix, int target, int row, int left, int right){
        boolean found = false;
        while(left<=right){
            int mid = (left+right)/2;
            if(matrix[row][mid]==target){
                found = true;
                break;
            }
            else if(matrix[row][mid]<target){
                left = mid + 1;
            }
            else{
                right = mid - 1;
            }
        }
        return found;
    }
}
