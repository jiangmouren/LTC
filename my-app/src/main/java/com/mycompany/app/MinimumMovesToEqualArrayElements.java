package com.mycompany.app;

/**
 * https://leetcode.com/problems/minimum-moves-to-equal-array-elements/
 * Given a non-empty integer array of size n,
 * find the minimum number of moves required to make all array elements equal,
 * where a move is incrementing n - 1 elements by 1.
 *
 * Example:
 * Input:
 * [1,2,3]
 * Output:
 * 3
 *
 * Explanation:
 * Only three moves are needed (remember each move increments two elements):
 * [1,2,3]  =>  [2,3,3]  =>  [3,4,3]  =>  [4,4,4]
 */
public class MinimumMovesToEqualArrayElements {
    //这是十分“骚气”的一道题！
    //这道题最好的理解方式是“运动是相对的”.
    //思考每次往上移动(n-1)个，跟思考每次往下移动1个，效果是一样的
    //所以为了“拉平”，每次挑最大的那个，把它拉到底
    //所以结果就是把每个数跟最小值的差，求和: Sum(a[i]-min) = Sum(a[i]) - Sum(min) = Sum(a[i]) - n*min
    public int minMoves(int[] nums) {
        int min = Integer.MAX_VALUE;
        int sum = 0;
        for(int num : nums){
            min = Math.min(min, num);
            sum += num;
        }
        return sum-min*nums.length;
    }
}
