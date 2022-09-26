package com.mycompany.app.JPMS;
import java.util.*;

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
 * 典型的Jumping pointer or monotonic stack.
 */
public class DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int l = temperatures.length;
        int[] ptrs = new int[l];
        int[] res = new int[l];
        ptrs[l-1] = l;
        for(int i=l-2; i>=0; i--){
            int ptr = i+1;
            while(ptr<l){
                if(temperatures[i]<temperatures[ptr]){
                    ptrs[i] = ptr;
                    break;
                }
                ptr = ptrs[ptr];
            }
            if(ptr>=l){
                ptrs[i] = l;
            }
            res[i] = ptrs[i]==l ? 0 : ptrs[i] - i;
        }
        return res;
    }

    public int[] dailyTemperaturesMonotonicStack(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temperatures.length];
        for(int i = temperatures.length-1; i>=0; i--){
            while(!stack.isEmpty() && temperatures[stack.peek()]<=temperatures[i]){
                stack.pop();
            }
            if(stack.isEmpty()){
                res[i] = 0;
            }
            else{
                res[i] = stack.peek() - i;
            }
            stack.push(i);
        }
        return res;
    }
}
