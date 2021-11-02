package com.mycompany.app.greedy;

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
        //这个题的“题眼”就在于对efficiency的使用规则上
        //因为要求一组engineers只能使用他们当中efficiency最小的那个的efficiency作为全组的efficiency
        //那么从每个不同的efficiency视角来看，能使用我这个efficiency所能达到的最大效果，
        //就是包含我自己在内的，所有efficiency大于等于我的engineer在group size不超过k的前提下，
        //所能找到的最大的speedSum
        //因为所有的speed都是non-negtive value，所以对于任意一个给定的efficiency来说，
        //我们的目标应该是：find the maxSum of the K-1 engineers' speed, in the group of engineers with larger efficiency
        //Of course if there are less than k-1 engineers, whose efficiency numbers are larger than me, just sum them all
        //这样分析完，这个问题基本变成了如果对efficicency从大到小sort之后，从左到右找running max k numbers,用一个PriorityQueue就可以解决
        //这个题还有一个要特别注意的点在于“at most k”，而不是"exact k"

        //这个问题如果从头开始分析，应该是一个dp的思路，首先是怎么拆分问题？
        //针对不同的speedGroup还是根据不同的efficiency?原则就是哪种分法产生的subprolem的数量最少。
        //如果按照前者分我又C(n,k)种分法，如果按照后者分，我有n种分法
        //这样分完了之后，构建原问题的解就是找这些子问题的最大值
        //然后考虑每一个子问题怎么解？然后就是根据前面的分析，所有efficiency比我大的engineer都可以都可以跟我构成一个组，没构成一个组就一个当下子问题的子问题
        //要解决当下子问题，就是要求上面所有子问题的最大值，我并不需要把这些子问题都解决掉，我只需要找到(k-1)sum最大的那个子问题，其余的子问题可以直接丢掉
        //这部分思想是greedy的味道
        //相当于根于efficiency分完之后的每个子问题，其实也只产生了一个子问题需要被解决，所以依然之后n个子问题需要被解决
        //余下的问题就是这n个子问题之间有没有toplogical dependency？
        //显然答案是有。从efficiency比较大的子问题，可以O(lgK)的推出efficiency比较小的子问题，使用priorityQueue.

        int[][] pairs = new int[n][2];
        for(int i=0; i<n; i++){
            pairs[i][0] = efficiency[i];
            pairs[i][1] = speed[i];
        }
        Arrays.sort(pairs, (a, b)->b[0]-a[0]);
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        long res = 0;//这里要注意使用long存储中间变量，因为题目已经提醒数字过大，有溢出风险
        long sum = 0;
        for(int[] pair : pairs){
            pq.add(pair[1]);
            sum += pair[1];
            res = Math.max(res, sum*pair[0]);
            if(pq.size()>=k){
                sum -= pq.poll();
            }
        }
        return (int)(res % (long)(1e9+7));//这里就是控制输出与量的大小，始终控制在[0, 1e9+6]
    }
}
