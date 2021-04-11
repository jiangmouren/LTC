package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/minimum-cost-to-hire-k-workers/
 * There are N workers.  The i-th worker has a quality[i] and a minimum wage expectation wage[i].
 * Now we want to hire exactly K workers to form a paid group.
 * When hiring a group of K workers, we must pay them according to the following rules:
 * Every worker in the paid group should be paid in the ratio of their quality compared to other workers in the paid group.
 * Every worker in the paid group must be paid at least their minimum wage expectation.
 * Return the least amount of money needed to form a paid group satisfying the above conditions.
 *
 * Example 1:
 * Input: quality = [10,20,5], wage = [70,50,30], K = 2
 * Output: 105.00000
 * Explanation: We pay 70 to 0-th worker and 35 to 2-th worker.
 *
 * Example 2:
 * Input: quality = [3,1,10,10,1], wage = [4,8,2,2,7], K = 3
 * Output: 30.66667
 * Explanation: We pay 4 to 0-th worker, 13.33333 to 2-th and 3-th workers seperately.
 *
 * Note:
 * 1 <= K <= N <= 10000, where N = quality.length = wage.length
 * 1 <= quality[i] <= 10000
 * 1 <= wage[i] <= 10000
 * Answers within 10^-5 of the correct answer will be considered correct.
 */

//这个问题跟https://leetcode.com/problems/maximum-performance-of-a-team 很像
//这里ratio对应那边的efficiency，这里的quality对应那里的speed
//这里的每个子问题的解释用一组当中最大的ratio * sum(quality)
//要注意跟前者的一个不同是这里找的是"exactly k" instead of "at most k"
public class MinimumCostToHireKWorkers {
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        int n = quality.length;
        double[][] candidates = new double[n][2];
        for(int i=0; i<n; i++){
            candidates[i][0] = (double)quality[i];
            candidates[i][1] = (double)wage[i]/(double)quality[i];
        }

        //The "max" ratio will be used for the group of K, so sort by ratio in ascending order, different from the "team max performance", which use "min" efficiency
        //Arrays.sort(candidates, (a, b)->(int)(a[1]-b[1]));//这里不能这么直接cast成int，因为a[1]-b[1]有可能是0.5这样的小数，然后就被cast成0了
        Arrays.sort(candidates, (a, b)->{
            double cmp = a[1] - b[1];
            if(cmp>0){
                return 1;
            }
            else if(cmp<0){
                return -1;
            }
            else{
                return 0;
            }
        });

        //for(double[] candidate : candidates){
        //    System.out.println("quality: "+candidate[0]+" "+"ratio: "+candidate[1]);
        //}

        //maxHeap
        PriorityQueue<Double> q = new PriorityQueue<>(K, (a, b)->(int)(b-a));

        double res = Double.MAX_VALUE;
        double sum = 0.0;
        for(double[] candidate : candidates){
            sum += candidate[0];
            q.add(candidate[0]);
            if(q.size()>=K){//注意这里跟team max performance那道题不一样，这一要求是"exact" k, not "at most" k
                res = Math.min(res, candidate[1]*sum);
                sum -= q.poll();
            }
        }
        return res;
    }
}
