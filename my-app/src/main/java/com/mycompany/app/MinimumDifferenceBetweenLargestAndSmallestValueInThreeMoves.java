package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/minimum-difference-between-largest-and-smallest-value-in-three-moves/
 * Given an array nums, you are allowed to choose one element of nums and change it by any value in one move.
 * Return the minimum difference between the largest and smallest value of nums after perfoming at most 3 moves.
 *
 * Example 1:
 * Input: nums = [5,3,2,4]
 * Output: 0
 * Explanation: Change the array [5,3,2,4] to [2,2,2,2].
 * The difference between the maximum and minimum is 2-2 = 0.
 *
 * Example 2:
 * Input: nums = [1,5,0,10,14]
 * Output: 1
 * Explanation: Change the array [1,5,0,10,14] to [1,1,0,1,1].
 * The difference between the maximum and minimum is 1-0 = 1.
 *
 * Example 3:
 * Input: nums = [6,6,0,1,1,4,6]
 * Output: 2
 *
 * Example 4:
 * Input: nums = [1,5,6,14,15]
 * Output: 1
 *
 * Constraints:
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
public class MinimumDifferenceBetweenLargestAndSmallestValueInThreeMoves {
    //本质上，这个题需要先sort，然后选在3个数做改变，使得这一组数的散布区间最小
    //要使得散布区间最小，肯定要把两头的数往中间移，因为可以移动任意量，其实就可以当成可以把两头的数干掉
    //一共就4种可能：左边取i个， 右边取j个，i+j=3，然后找这4种可能里面多能获得的最小散布区间。
    //形式结构类似：https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
    public int minDifference(int[] A) {
        if(A.length<5){
            return 0;
        }
        Arrays.sort(A);
        int res = Integer.MAX_VALUE;
        for(int i=0; i<4; i++){
            int j = 3 - i;
            res = Math.min(res, A[A.length-1-j]-A[i]);
        }
        return res;
    }
}
