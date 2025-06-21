package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 * You are given an array of integers nums, there is a sliding window of size k which is moving from the very left of the array to the very right.
 * You can only see the k numbers in the window. Each time the sliding window moves right by one position.
 * Return the max sliding window.
 *
 * Example 1:
 * Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 * Window position                Max
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *
 * Example 2:
 * Input: nums = [1], k = 1
 * Output: [1]
 *
 * Example 3:
 * Input: nums = [1,-1], k = 1
 * Output: [1,-1]
 *
 * Example 4:
 * Input: nums = [9,11], k = 2
 * Output: [11]
 *
 * Example 5:
 * Input: nums = [4,-2], k = 2
 * Output: [4]
 *
 *
 * Constraints:
 * 1 <= nums.length <= 105
 * -104 <= nums[i] <= 104
 * 1 <= k <= nums.length
 */

public class SlidingWindowMaximum{
    /**
     * 这个Deque的使用非常巧妙!!!
     * 这个Deque的使用可以看成是单调栈的一种拓展
     * 简单的单调栈都是存从最左侧或者最右侧到当下位置累积的elements，而现在需要的是一个sliding window里面的elements
     * 所以就用一个deque，可以从头部把window外的element祛除掉。
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new ArrayDeque<>();
        int[] res = new int[nums.length-k+1];
        for(int i=0; i<nums.length; i++){
            updateDeque(i, k, deque, nums);
            if(i>=k-1){
                res[i-k+1] = deque.getFirst();
            }
        }
        return res;
    }

    private void updateDeque(int i, int k, Deque<Integer> deque, int[] nums){
        if(i>=k){
            if(nums[i-k]==deque.getFirst()){
                deque.removeFirst();
            }
        }
        //注意这里必须不能包含等号，也就是说相等的时候要存多份，否则上面的左侧移除逻辑就错了
        while(!deque.isEmpty() && deque.getLast()<nums[i]){
            deque.removeLast();
        }
        deque.addLast(nums[i]);
    }

    /**
     * 下面这个dp的构造就更巧妙了！！！
     * 入下图所示：
     * src\main\resources\MaximumSlidingWindow2.PNG
     * 如果把nums[]从左到右每个长度k分成一段，最后不足k的自己也是一段，
     * 那么对于任意一个位置的slidingWindow(入图中括号位置)，其都可以看成是由上面分好的段，一个prefix部分 + 一个suffix部分构成的
     * 而我只要知道上面知道上面每一个分段的prefixMax Array && suffixMax_Array就可以组合出任意位置的slidingWindowMax
     */
    public int[] maxSlidingWindowSln2(int[] nums, int k) {
        int[] left = new int[nums.length];
        int[] right = new int[nums.length];
        int max = Integer.MIN_VALUE;
        for(int i=0; i<nums.length; i++){
            if(i%k==0){
                max = Integer.MIN_VALUE;
            }
            max = Math.max(max, nums[i]);
            left[i] = max;
        }
        for(int i=nums.length-1; i>=0; i--){
            if(i==nums.length-1 || i%k==k-1){//注意从右往左写的时候，还是要遵循之前从左往右的分段！！！
                max = Integer.MIN_VALUE;
            }
            max = Math.max(max, nums[i]);
            right[i] = max;
        }
        int[] res = new int[nums.length-k+1];
        for(int i=0; i<=nums.length-k; i++){
            res[i] = Math.max(right[i], left[i+k-1]);
        }
        return res;
    }
}
