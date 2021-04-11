package com.mycompany.app;

/**
 * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
 * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of profit[i].
 * You're given the startTime, endTime and profit arrays,
 * return the maximum profit you can take such that there are no two jobs in the subset with overlapping time range.
 * If you choose a job that ends at time X you will be able to start another job that starts at time X.
 *
 * Example 1:
 * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
 * Output: 120
 * Explanation: The subset chosen is the first and fourth job.
 * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
 *
 * Example 2:
 * Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
 * Output: 150
 * Explanation: The subset chosen is the first, fourth and fifth job.
 * Profit obtained 150 = 20 + 70 + 60.
 *
 * Example 3:
 * Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
 * Output: 6
 *
 *
 * Constraints:
 *
 * 1 <= startTime.length == endTime.length == profit.length <= 5 * 104
 * 1 <= startTime[i] < endTime[i] <= 109
 * 1 <= profit[i] <= 104
 */

import java.util.*;

/**
 * 这种解法算是DP的一种进阶版：
 * 不再是规整的数组的形式，母问题与子问题的“状态”之间的联系，不再能用简单的index来表达。
 * 而是要通过binary search的形式去“寻找”自己的子问题对应的状态。
 * 这里有两个概念：“子问题”和“状态”。
 * 以及由着两个概念引出的两对关系：“母子问题之间的构造关系”和“上述构造关系所对应的状态转移关系”。
 * 这个题目和Knapsack problem是一回事都是如何fit in最多的东西最大化某个值。
 * 这里面的子问题是有或者没有某个job的方式构建出来的，只是状态转移方程多了一层“函数”。
 * 其状态转移方程不是dp[n] = f(dp[n-1], dp[n-k])这种“固定”的母问题和子问题的构造关系。
 * 而是dp[n] = f(dp[n-1], dp[g(n)])，既母问题所对应的子问题不是固定的，而是由一个函数确定的。
 * 这个问题当中g()就是要找到在当下job开始时间之前最后结束那个job为结束的子问题。
 * 就这个问题的“状态”而言，需要同时记录两个维度的信息：最大值，对应的停止时间。
 * 再就是要区分要存2个状态跟2-D dp区别。
 * 前者是有n个子问题，但是描述每个问题需要两个状态变量,后者是有n*n个子问题，但是每个子问题只需要一个状态变量.
 * 参考：https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
 * 本质上这是greedy的写法。
 */
public class MaximumProfitInJobScheduling {
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        //构建子问题
        int[][] jobs = new int[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new int[] {startTime[i], endTime[i], profit[i]};
        }
        Arrays.sort(jobs, (a, b)->a[1] - b[1]);
        //求解状态空间
        TreeMap<Integer, Integer> dp = new TreeMap<>();
        dp.put(0, 0);
        for (int[] job : jobs) {
            int cur = dp.floorEntry(job[0]).getValue() + job[2];
            //只有当cur更大的时候才能去更新dp，千万不能cur没有跟大的时候，dp.put(job[1], dp.lastEntry().getValue())
            //因为这样错误改变了系统的状态，因为“时间状态”所表达的不是“截止某个时间”，而是“取得最大值的结束时间”.
            //并不需要每个子问题都要在状态空间里面有一个对应的entry，而只是需要每个子问题去更新状态空间，使其能够有效表达每个子问题的解。
            //具体到这个问题来说每个job过完之后，dp.lastEntry().getValue()就是截止这个job所能获得的最大值。
            //但这个取得这个最大值的组合需要drop合格job，那么这个job本身就不需要对dp做任何改动，因为其解，已经反映在其中。
            if (cur > dp.lastEntry().getValue())
                dp.put(job[1], cur);
        }
        return dp.lastEntry().getValue();
    }

    /**
     * 上面的是利用了TreeMap(red-black tree)，就不需要自己写binary search了。
     * 下面是用list自己写binary search.
     */
    class Job {
        int start;
        int end;
        int profit;

        public Job(int start, int end, int profit) {
            this.start = start;
            this.end = end;
            this.profit = profit;
        }
    }

    public int jobSchedulingSln2(int[] startTime, int[] endTime, int[] profit) {
        // first sort jobs by end_time to make it easy to process each job and fill DP table
        int n = profit.length;
        Job[] jobs = new Job[n];
        for (int i = 0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }

        Arrays.sort(jobs, (j1, j2) -> Integer.compare(j1.end, j2.end));
        List<Integer> dpEndTime = new ArrayList<>();
        List<Integer> dpProfit = new ArrayList<>();
        dpEndTime.add(0); // base case to avoid IndexOutBoundExp
        dpProfit.add(0);

        for (Job job : jobs) {
            int prevIdx = largestSmaller(dpEndTime, job.start + 1); // previous job can end at job.start, so we use "job.start + 1"
            int case2Profit = dpProfit.get(prevIdx) + job.profit;
            int case1Profit = dpProfit.get(dpProfit.size() - 1);

            if (case2Profit > case1Profit) {
                dpEndTime.add(job.end);
                dpProfit.add(case2Profit);
            }
        }

        return dpProfit.get(dpProfit.size() - 1);
    }

    // return the index of the largest element < target in the given list (assume there must exist one element < target)
    private int largestSmaller(List<Integer> list, int target) {
        int left = 0;
        int right = list.size() - 1;

        while (left + 1 < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) < target) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return list.get(right) < target ? right : left;
    }
}
