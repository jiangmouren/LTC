package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/leftmost-column-with-at-least-a-one/
 * A row-sorted binary matrix means that all elements are 0 or 1 and each row of the matrix is sorted in non-decreasing order.
 * Given a row-sorted binary matrix binaryMatrix, return the index (0-indexed) of the leftmost column with a 1 in it.
 * If such an index does not exist, return -1.
 *
 * You can't access the Binary Matrix directly. You may only access the matrix using a BinaryMatrix interface:
 *
 * BinaryMatrix.get(row, col) returns the element of the matrix at index (row, col) (0-indexed).
 * BinaryMatrix.dimensions() returns the dimensions of the matrix as a list of 2 elements [rows, cols], which means the matrix is rows x cols.
 * Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer.
 * Also, any solutions that attempt to circumvent the judge will result in disqualification.
 *
 * For custom testing purposes, the input will be the entire binary matrix mat.
 * You will not have access to the binary matrix directly.
 *
 * Example 1:
 * Input: mat = [[0,0],[1,1]]
 * Output: 0
 *
 * Example 2:
 * Input: mat = [[0,0],[0,1]]
 * Output: 1
 *
 * Example 3:
 * Input: mat = [[0,0],[0,0]]
 * Output: -1
 *
 * Example 4:
 * Input: mat = [[0,0,0,1],[0,0,1,1],[0,1,1,1]]
 * Output: 1
 *
 *
 * Constraints:
 * rows == mat.length
 * cols == mat[i].length
 * 1 <= rows, cols <= 100
 * mat[i][j] is either 0 or 1.
 * mat[i] is sorted in non-decreasing order.
 */

public class LeftmostColumnWithALeastAOne {
    class BinaryMatrix{
        int[][] matrix;
        public BinaryMatrix(int row, int col){
            this.matrix = new int[row][col];
        }

        public List<Integer> dimensions(){
            List<Integer> res = new ArrayList<>();
            res.add(this.matrix.length);
            res.add(this.matrix[0].length);
            return res;
        }

        public int get(int row, int col){
            return matrix[row][col];
        }
    }
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        //这个题挺有意思：call BinaryMatrix.get(row, col)不能超过1000次
        //matrix的size最大是100*100，所以就是不能遍历
        //因为每一行都是sorted，所以考虑做binary search
        //计算一下次数：bianry search的complexity是O(logN)，对于一行100个数，2^7=128，所以一行查找最多需要call 7次
        //100行，最多call 700次，所以满足条件

        List<Integer> size = binaryMatrix.dimensions();
        int rows = size.get(0);
        int cols = size.get(1);
        int res = Integer.MAX_VALUE;
        for(int i=0; i<rows; i++){
            int temp = binarySearch(binaryMatrix, i, 0, cols-1);
            if(temp!=-1){
                System.out.println("here");
                res = Math.min(res, temp);
            }
        }
        if(res!=Integer.MAX_VALUE){
            return res;
        }
        else{
            return -1;
        }
    }

    //下面这种recursive的写法，保证每一个call stack里面只call matrix.get()一次，所以最优的。
    private int binarySearch(BinaryMatrix matrix, int rowNum, int left, int right){
        //termination
        if(left>right){
            return -1;
        }

        int mid = (left + right)/2;
        if(matrix.get(rowNum, mid)==0){
            return binarySearch(matrix, rowNum, mid+1, right);
        }
        else{
            int rightRes = binarySearch(matrix, rowNum, left, mid-1);
            if(rightRes==-1){
                return mid;
            }
            else{
                return rightRes;
            }
        }
    }

    //底下这种iterative的Binary Search的写法，虽然通过了，其实并不Optimal，因为在mid是1的时候，会多call一次matrix.get()
    //要作为改动的话就是要parameter里面在加一个flag来记录是否已经找到1, 然后在while loop开始的时候，最右端是否为0，且已经found
    //这种情况就return right+1，但和又多加了一在while loop开始的call，而且代码很messy.不好！
    private int binarySearchIter(BinaryMatrix matrix, int rowNum, int left, int right){
        int res = -1;
        boolean found = false;
        while(left<=right){
            int mid = (left+right)/2;
            if(matrix.get(rowNum, mid)==0){
                left = mid + 1;
            }
            else{
                if(mid-1<left || matrix.get(rowNum, mid-1)==0){
                    res = mid;
                    break;
                }
                else{
                    right = mid - 1;
                    continue;
                }
            }
        }
        return res;
    }

}
