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
        if(intervals.length==1){
            return intervals;
        }
        //Use lambda to pass in a comparator
        Arrays.sort(intervals, (a, b) -> a[0]-b[0]);
        int start = intervals[0][0];
        int end = intervals[0][1];
        List<int[]> res = new ArrayList<>();
        int ptr = 1;
        while(ptr<intervals.length){
            if(intervals[ptr][0]<=end){
                end = Math.max(end, intervals[ptr][1]);
            }
            else{
                int[] temp = new int[2];
                temp[0] = start;
                temp[1] = end;
                res.add(temp);
                start = intervals[ptr][0];
                end = intervals[ptr][1];
            }
            ptr++;
        }

        //Don't forget to add the last start & end
        res.add(new int[]{start, end});
        int[][] result = new int[res.size()][2];
        result = res.toArray(result);
        return result;
    }
}
