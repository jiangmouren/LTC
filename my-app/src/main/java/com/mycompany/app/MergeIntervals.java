package com.mycompany.app;
/**
 * https://leetcode.com/problems/merge-intervals/submissions/
 * Given a collection of intervals, merge all overlapping intervals.
 * For example,
 * Given [1,3],[2,6],[8,10],[15,18],
 * return [1,6],[8,10],[15,18].
 */

/**
 * Analysis:
 * All you need to do is to sort it, so you can avoid O(n^2)
 */

import java.util.*;

public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        //After java 8, you can use lambda function instead of implement comparator.
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        List<int[]> resultList = new ArrayList<>();
        int ptr0 = 0;
        int ptr1 = 0;
        int end = intervals[0][1];
        while(ptr1<intervals.length){
            if(intervals[ptr1][0]<=end && intervals[ptr1][1]>end){
                end = intervals[ptr1][1];
            }
            else if(intervals[ptr1][0]>end){
                int[] temp = {intervals[ptr0][0], end};
                resultList.add(temp);
                ptr0 = ptr1;
                end = intervals[ptr0][1];
            }
            ptr1++;
        }
        //add the last interval
        resultList.add(new int[]{intervals[ptr0][0], end});
        int[][] result = new int[resultList.size()][resultList.get(0).length];
        result = resultList.toArray(result);
        return result;
    }
}
