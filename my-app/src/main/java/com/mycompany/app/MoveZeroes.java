package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/move-zeroes/
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 *
 * Example:
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Note:
 *
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 */

public class MoveZeroes{
    //很实用的小技巧！！！
    public void moveZeroes(int[] nums) {
        //用一个rd_ptr和一个wr_ptr，然后读到一个非零的，就写一个位置，rd到底的时候，就开始在wr连续写零知道结尾
        int rdPtr = 0;
        int wrPtr = 0;
        while(rdPtr<nums.length){
            if(nums[rdPtr]!=0){
                nums[wrPtr] = nums[rdPtr];
                wrPtr++;
            }
            rdPtr++;
        }
        while(wrPtr<nums.length){
            nums[wrPtr] = 0;
            wrPtr++;
        }
    }

    //Naive solution, O(n^2)
    public void moveZeroesBad(int[] nums) {
        for(int i=nums.length-1; i>=0; i--){
            if(nums[i]==0){
                sink(nums, i);
            }
        }
    }

    private void sink(int[] nums, int pos){
        while(pos+1<nums.length && nums[pos+1]!=0){
            int temp = nums[pos];
            nums[pos] = nums[pos+1];
            nums[pos+1] = temp;
            pos++;
        }
    }
}