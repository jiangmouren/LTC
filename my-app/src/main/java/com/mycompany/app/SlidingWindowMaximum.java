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
     * 用这个deque存储的是running maximum candidates，可以看做是"running maximum"的一个拓展。
     * 然后其complexity是O(n)，并没有因为要回头去deque里面把比当前小的entry都删掉，而变成O(nk).
     * 其原因，与内在的过程与“LargestRectangleInHistogram”里面构造left[] & right[]时是一样的。
     * 如在下图中：
     * src\main\resources\MaximumSlidingWindow.PNG
     * 0进deque不需要比较，进入cost为0
     * 1进入需要跟0做一次比较，进入cost为1
     * 2进入需要跟1做一次比较，进入cost为1
     * 3进入需要跟2做一次比较，进入cost为1
     * 4进入，需要跟3，2，1，0都发生一次比较，跟3,2,1的比较算作3,2,1删除的cost，各自为1, 跟0的比较算作4进入的cost为1
     * 5跟4， 0都有比较，跟4的比较算在4的删除cost，跟0的比较算作5的进入cost。
     * 6根5， 0斗鱼比较，跟6的比较算作5的删除cost，跟0的比较算作0的删除cost, 然后6进入跟0进入一样没有cost
     * 像上面这样搞清楚cost是如何amortized很重要！！！否则如果只是祖略的看每个element被操作了几次，并不能直接得出整个过程
     * 就是O(n)的结论。因为比如上图0的位置，就被比较了很多次。但通过上面的分析，可以看到所有这些的operation都可以分摊到不同的
     * element的entry/exit cost上去，而且amortize之后每个element的entry && exit cost都是O(1)的。
     * 所以这整个过程就是O(n)的。
     * 前面说这个过程跟“LargestRectangleInHistogram”里面构造left[] & right[]时是一样的，
     * 这里往回删的时候是遇到比自己大的就停止，LargestRectangleInHistogram里面的情况是，遇到比自己小的就停止。
     * 这里是，通过直接删除的方式来跳过一些点，跟LargestRectangleInHistogram通过Pointer跳跃，
     * 或者是用stack直接删除效果是一样的。
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
