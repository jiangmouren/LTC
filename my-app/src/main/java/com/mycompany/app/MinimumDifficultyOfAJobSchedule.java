package com.mycompany.app;

/**
 * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
 * You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the i-th job, you have to finish all the jobs j where 0 <= j < i).
 * You have to finish at least one task every day. The difficulty of a job schedule is the sum of difficulties of each day of the d days. The difficulty of a day is the maximum difficulty of a job done in that day.
 * Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is jobDifficulty[i].
 * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
 *
 * Example 1:
 * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
 * Output: 7
 * Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
 * Second day you can finish the last job, total difficulty = 1.
 * The difficulty of the schedule = 6 + 1 = 7
 *
 * Example 2:
 * Input: jobDifficulty = [9,9,9], d = 4
 * Output: -1
 * Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given jobs.
 *
 * Example 3:
 * Input: jobDifficulty = [1,1,1], d = 3
 * Output: 3
 * Explanation: The schedule is one job per day. total difficulty will be 3.
 *
 * Example 4:
 * Input: jobDifficulty = [7,1,7,1,7,1], d = 3
 * Output: 15
 *
 * Example 5:
 * Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
 * Output: 843
 *
 * Constraints:
 * 1 <= jobDifficulty.length <= 300
 * 0 <= jobDifficulty[i] <= 1000
 * 1 <= d <= 10
 *
 * 这个属于典型的代码很简单，逻辑很复杂的类型。而逻辑复杂主要是细节上的复杂。
 * 大的思路其实很简单，就是按照最后一天所有的可能性把问题拆解。
 * 其拓扑结构和复杂性分析详见：src\main\resources\MinJobScheduleComplexity.PNG
 */
public class MinimumDifficultyOfAJobSchedule {
    public int minDifficulty(int[] jobDifficulty, int d) {
        //dp[n][d] = min{max{jobDifficulty[n-1]}+dp[n-1][d-1], max{jobDifficulty[n-1], jobDifficulty[n-2]}+dp[n-2][d-1], ... max{jobDifficulty[n-1], jobDifficulty[n-2], ... jobDifficulty[d-1]}+dp[d-1][d-1]};
        //在上面的式子里还要注意到：max{jobDifficulty[n-1], jobDifficulty[n-1], ... jobDifficulty[d]}部分是surffix的最大值问题，本身也是个子dp问题
        //初值： dp[1][1] = max{jobDifficulty[0]}
        //      dp[2][1] = max{jobDifficulty[0], jobDifficulty[1]}
        //     dp[n-d+1][1] = max{jobDifficulty[n-d], jobDifficulty[n-d-1], ... jobDifficulty[0]}
        //注意上面的初值并不是整个第一列，而是只取了一段，这是由于递推关系里面只需要这一段做初值。也可以算正列，就是不够优化而已。
        //上面的初值求解本身是一个prefix最大值问题。
        //为了方便理解，这里dp[i][j]中的i&j分别指的是剩余长度：剩余i个job，剩余j天，而且j>=i
        //如此dp[][]就要把x&y的size个增大1。
        //具体implementation上的topological order，从左到右，一列一列，每一列从大到小（因为递推关系里面的surffix最大值问题需要这个顺序）
        int n = jobDifficulty.length;
        //special case
        if(d>n){
            return -1;
        }

        int[][] dp = new int[n+1][d+1];
        //set up initial values
        for(int i=1; i<=n-d+1; i++){//注意dp是1-based，而jobDifficulty是0-based
            dp[i][1] = Math.max(jobDifficulty[i-1], dp[i-1][1]);//dp[0][1]本身是个没有意义的entry，刚好初值是0，不影响求解dp[1][1]
        }

        //get reset of dp entries
        for(int j=2; j<=d; j++){
            for(int i=j+n-d; i>=j; i--){
                int sufMax = 0;
                dp[i][j] = Integer.MAX_VALUE;
                for(int k=i-1; k>=j-1; k--){
                    //jobDifficulty是0-based,所以k从i-1到j-1，刚好对应的是第i到第j个Job
                    sufMax = Math.max(sufMax, jobDifficulty[k]);
                    dp[i][j] = Math.min(dp[i][j], sufMax+dp[k][j-1]);
                }
            }
        }
        return dp[n][d];
    }
}
