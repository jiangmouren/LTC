package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
 * There is a one-dimensional garden on the x-axis.
 * The garden starts at the point 0 and ends at the point n. (i.e The length of the garden is n).
 * There are n + 1 taps located at points [0, 1, ..., n] in the garden.
 * Given an integer n and an integer array ranges of length n + 1,
 * where ranges[i] (0-indexed) means the i-th tap can water the area [i - ranges[i], i + ranges[i]] if it was open.
 * Return the minimum number of taps that should be open to water the whole garden,
 * If the garden cannot be watered return -1.
 *
 * Example 1:
 * Input: n = 5, ranges = [3,4,1,1,0,0]
 * Output: 1
 * Explanation: The tap at point 0 can cover the interval [-3,3]
 * The tap at point 1 can cover the interval [-3,5]
 * The tap at point 2 can cover the interval [1,3]
 * The tap at point 3 can cover the interval [2,4]
 * The tap at point 4 can cover the interval [4,4]
 * The tap at point 5 can cover the interval [5,5]
 * Opening Only the second tap will water the whole garden [0,5]
 *
 * Example 2:
 * Input: n = 3, ranges = [0,0,0,0]
 * Output: -1
 * Explanation: Even if you activate all the four taps you cannot water the whole garden.
 *
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 * ranges.length == n + 1
 * 0 <= ranges[i] <= 100
 */
public class MinimumNumberOfTapsToOpenToWaterAGarden {
    //最简单是下面一种DP的解法，这种解法乍看起来简单，但是里面的topological order理解上有点tricky
    public int minTaps(int n, int[] A) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 2);
        dp[0] = 0;
        for (int i = 0; i <= n; ++i)
            for (int j = Math.max(i - A[i] + 1, 0); j <= Math.min(i + A[i], n); ++j)
                dp[j] = Math.min(dp[j], dp[Math.max(0, i - A[i])] + 1);
        return dp[n]  < n + 2 ? dp[n] : -1;
    }

    //下面这种写法跟我自己想到的用mono-stack的解法，本质上是一个思路，但是在实现上，更优。
    //time complexity O(NlogN), space complexity因为getIntervals()的原因都是O(N)，但后面做iteration的时候，space复杂度是O(1)
    public int minTapsGreedy(int n, int[] ranges){
        int[][] intervals = getIntervals(ranges);
        //for(int[] interval : intervals){
        //    System.out.println(interval[0]+" ,"+interval[1]);
        //}
        int start = 0;
        int end = 0;
        int ptr = 0;
        int cnt = 0;
        while(ptr<ranges.length){
            while(ptr<ranges.length && intervals[ptr][0]<=start){
                end = Math.max(end, intervals[ptr][1]);
                ptr++;
            }
            //System.out.println(end);
            if(end==start){
                if(end>=n){
                    return cnt;//没往前进，所以就不选任何新东西
                }
                else{
                    return -1;
                }
            }
            else{
                cnt++;
                if(end>=n){
                    return cnt;
                }
                start = end;
            }
        }
        return cnt;
    }

    //我的解法就是基于interval操作的思路，生成所有的interval，然后根据左端点sort，然后我就可以用greedy的思路从左到右scan一遍
    //过程中需要做两个决定：1. 对于有贡献的interval，是存全部，还是它贡献的部分；2. 新的interval来之后，如何祛除旧的，没有继续存在必要的interval
    //以上两点综合考虑，选择只存贡献的部分，然后就可以用mono-stack的办法，把已经不再继续贡献的interval祛除
    //time complexity O(NlogN), space complexity O(N)
    public int minTapsMonoStack(int n, int[] ranges) {
        //get sorted intervals
        int[][] intervals = getIntervals(ranges);
        //for(int[] interval : intervals){
        //    System.out.println(interval[0]+" ,"+interval[1]);
        //}
        Stack<int[]> stack = new Stack<>();
        //int left = 0;
        int right = 0;
        //n>=1, so need to check before interation
        for(int[] interval : intervals){
            if(stack.size()>0){
                right = stack.peek()[1];
            }
            //gap case
            if(interval[0]>right){
                return -1;
            }
            else{
                if(interval[1]>right){
                    while(!stack.isEmpty() && stack.peek()[0]>=interval[0]){
                        stack.pop();
                    }
                    if(stack.size()>0){
                        interval[0] = stack.peek()[1];//注意这里必须要连续，不能是"stack.peek()[1] + 1"
                    }
                    stack.add(interval);
                    if(interval[1]>=n){
                        return stack.size();
                    }
                }
            }
        }
        return -1;
    }

    private int[][] getIntervals(int[] ranges){
        int[][] res = new int[ranges.length][2];
        for(int i=0; i<ranges.length; i++){
            int left = Math.max(0, i-ranges[i]);
            int right = i+ranges[i];
            int[] interval = {left, right};
            res[i] = interval;
        }
        Arrays.sort(res, (a, b)->a[0]-b[0]);
        return res;
    }
}
