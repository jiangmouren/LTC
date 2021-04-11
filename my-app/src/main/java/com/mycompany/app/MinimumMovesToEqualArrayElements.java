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
    //举例[1, 3, 7, 4]来理解该怎么操作
    //首先对上述array做一个sort来帮助理解，实际并不需要sort,得到：[1, 3, 4, 7]
    //观察这个sort过的array，最大值是7，因为在move的过程中，是只增不减的，所以最起码应该把min加到7
    //得到:[7, 9, 10, 7]，原来的第二大，现在成了最大，同理下一步，最起码要加到10，得到[10, 12, 10, 10]，进而类推得到：[12, 12, 12, 12]
    //回顾上面每一步增加的了多少？实际上依次把每一个数与最小的值得差， aggregate起来，就是我们实际增加的量。
    //Sum(a[i]-min) for i=[0, n-1] --> {Sum(a[i]) for i=[0, n-1]} - n*min
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
