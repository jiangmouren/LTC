package com.mycompany.app;

/**
 * https://leetcode.com/problems/daily-temperatures/
 * Given a list of daily temperatures T, return a list such that, for each day in the input,
 * tells you how many days you would have to wait until a warmer temperature.
 * If there is no future day for which this is possible, put 0 instead.
 * For example, given the list of temperatures T = [73, 74, 75, 71, 69, 72, 76, 73],
 * your output should be [1, 1, 4, 2, 1, 1, 0, 0].
 * Note: The length of temperatures will be in the range [1, 30000].
 * Each temperature will be an integer in the range [30, 100].
 */

/**
 * 本质上是要找到右侧最近的比自己大的数的位置。可以用“Jumping Pointer”的办法，具体思路分析详见“SlidingWindowMaximum”
 * 这种办法整个的complexity是O(n)
 */
public class DailyTemperatures {
    public int[] dailyTemperatures(int[] T) {
        int n = T.length;
        int[] idx = new int[n];
        for(int i=n-1; i>=0; i--){
            if(i==n-1){
                idx[i] = n;
            }
            else{
                int ptr = i+1;
                while(ptr<n && T[i]>=T[ptr]){
                    ptr = idx[ptr];
                }
                idx[i] = ptr;
            }
        }

        for(int i=0; i<n; i++){
            if(idx[i]==n){
                idx[i] = 0;
            }
            else{
                idx[i] = idx[i] - i;
            }
        }
        return idx;
    }
}
