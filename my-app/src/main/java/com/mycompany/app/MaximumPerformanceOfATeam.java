package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/maximum-performance-of-a-team/
 * There are n engineers numbered from 1 to n and two arrays: speed and efficiency,
 * where speed[i] and efficiency[i] represent the speed and efficiency for the i-th engineer respectively.
 * Return the maximum performance of a team composed of at most k engineers,
 * since the answer can be a huge number, return this modulo 10^9 + 7.
 * The performance of a team is the sum of their engineers' speeds multiplied by the minimum efficiency among their engineers.
 *
 * Example 1:
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 2
 * Output: 60
 * Explanation:
 * We have the maximum performance of the team by selecting engineer 2 (with speed=10 and efficiency=4) and engineer 5 (with speed=5 and efficiency=7). That is, performance = (10 + 5) * min(4, 7) = 60.
 *
 * Example 2:
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 3
 * Output: 68
 * Explanation:
 * This is the same example as the first but k = 3. We can select engineer 1, engineer 2 and engineer 5 to get the maximum performance of the team. That is, performance = (2 + 10 + 5) * min(5, 4, 7) = 68.
 *
 * Example 3:
 * Input: n = 6, speed = [2,10,3,1,5,8], efficiency = [5,4,3,9,7,2], k = 4
 * Output: 72
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 * speed.length == n
 * efficiency.length == n
 * 1 <= speed[i] <= 10^5
 * 1 <= efficiency[i] <= 10^8
 * 1 <= k <= n
 */

/**
 * Analysis:
 * 极为风骚的一种Greedy解法。
 * 先根据efficiency从大到小sort，然后从左往右
 */
public class MaximumPerformanceOfATeam {
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        int[][] pairs = new int[n][2];
        for(int i=0; i<n; i++){
            pairs[i][0] = efficiency[i];
            pairs[i][1] = speed[i];
        }
        Arrays.sort(pairs, (a, b)->b[0]-a[0]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(k, (a, b)->a[1]-b[1]);
        long sum = 0;
        long max = -1;
        for(int i=0; i<n; i++){
            if(pq.size()<k){
                sum += pairs[i][1];
                pq.add(pairs[i]);
                max = Math.max(max, pairs[i][0]*sum);
            }
            else{
                long sumNew = sum - pq.peek()[1] + pairs[i][1];
                long prodNew = sumNew * pairs[i][0];
                max = Math.max(max, prodNew);
                pq.add(pairs[i]);
                sum = sum+pairs[i][1]-pq.poll()[1];
            }
        }
        return (int)(max % (long)(1e9+7));
    }
}
