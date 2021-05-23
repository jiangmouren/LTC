package com.mycompany.app;

/**
 * https://leetcode.com/problems/max-value-of-equation/
 * Given an array points containing the coordinates of points on a 2D plane, sorted by the x-values, where points[i] = [xi, yi] such that xi < xj for all 1 <= i < j <= points.length. You are also given an integer k.
 *
 * Find the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and 1 <= i < j <= points.length. It is guaranteed that there exists at least one pair of points that satisfy the constraint |xi - xj| <= k.
 *
 *
 *
 * Example 1:
 *
 * Input: points = [[1,3],[2,0],[5,10],[6,-10]], k = 1
 * Output: 4
 * Explanation: The first two points satisfy the condition |xi - xj| <= 1 and if we calculate the equation we get 3 + 0 + |1 - 2| = 4. Third and fourth points also satisfy the condition and give a value of 10 + -10 + |5 - 6| = 1.
 * No other pairs satisfy the condition, so we return the max of 4 and 1.
 * Example 2:
 *
 * Input: points = [[0,0],[3,0],[9,2]], k = 3
 * Output: 3
 * Explanation: Only the first two points have an absolute difference of 3 or less in the x-values, and give the value of 0 + 0 + |0 - 3| = 3.
 *
 *
 * Constraints:
 *
 * 2 <= points.length <= 10^5
 * points[i].length == 2
 * -10^8 <= points[i][0], points[i][1] <= 10^8
 * 0 <= k <= 2 * 10^8
 * points[i][0] < points[j][0] for all 1 <= i < j <= points.length
 * xi form a strictly increasing sequence.
 */

import java.util.*;

/**
 * 我们只往前看，往后的问题等到后面往前看的时候就解决了，类似于2-Sum
 * Because xi < xj,
 * yi + yj + |xi - xj| = (yi - xi) + (yj + xj)
 * So for each pair of (xj, yj),
 * we have xj + yj, and we only need to find out the maximum yi - xi.
 * To find out the maximum element in a sliding window,
 * we can use Priority Queue, TreeMap or Monotonic Stack.
 */
public class MaxValueOfEquation {
    public int findMaxValueOfEquationPriorityQueue(int[][] points, int k) {
        //arr[0]: yi-xi; arr[1]: xi;
        //PriorityQueue default is minHeap, 我们需要的是maxHeap for yi-xi
        //这个pq里面会出现y-x duplicate的情况, 如果不在comparator里面把顺序写清楚，就是arbitrary的，在相等的时候，x小的排前面.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]));
        int res = Integer.MIN_VALUE;
        for (int[] point : points) {
            while (!pq.isEmpty() && point[0] - pq.peek()[1] > k) {
                pq.poll();
            }
            if (!pq.isEmpty()) {
                res = Math.max(res, pq.peek()[0] + point[0] + point[1]);
            }
            pq.add(new int[]{point[1] - point[0], point[0]});
        }
        return res;
    }

    public int findMaxValueOfEquationMonoStack(int[][] points, int k) {
        Deque<int[]> ms = new ArrayDeque<>();
        int res = Integer.MIN_VALUE;
        for(int[] point : points){
            while(!ms.isEmpty() && point[0]-ms.peekFirst()[1]>k){
                ms.pollFirst();
            }
            if(!ms.isEmpty()){
                res = Math.max(res, ms.peekFirst()[0]+point[0]+point[1]);
            }
            while(!ms.isEmpty() && ms.peekLast()[0]<=point[1]-point[0]){//等于的也被干掉，留着对后面的没有意义了
                ms.pollLast();
            }
            ms.add(new int[]{point[1]-point[0], point[0]});
        }
        return res;
    }

    //TODO: ADD TreeMap Solution.
}
