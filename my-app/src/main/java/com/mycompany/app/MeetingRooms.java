package com.mycompany.app;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * determine if a person could attend all meetings.
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return false.
 * Definition for an interval.
 * public class Interval {
 *     int start;
 *     int end;
 *     Interval() { start = 0; end = 0; }
 *     Interval(int s, int e) { start = s; end = e; }
 * }
 */

/**
 * Analysis:
 * This question makes me think of the "shortest word distance" problem.
 * In both cases, the naive way would be compare each pair.
 * The way to avoid this is to get the array sorted.
 * In the "shortest word distance" case, the point of interest is the index of a word,
 * which is intrinsically sorted.
 * Once the array is sorted, we only care about what's near me, or the local result.
 * To obtain the global result, we just keep a running local result and scan the whole array.
 * In this case, the running result is "if there is a overlap so far".
 * In the other case, the running result is "current min".
 *
 * Same approach as seen in divide and conquer, and DP: split into sub-problems, then construct
 * global result from local results.
 *
 */

import java.util.*;

public class MeetingRooms {
    //The reasons why making Interval "static":
    //1. Not an instance member
    //2. A helper class, make it static so outside can access easily.
    public static class Interval {
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }
    public boolean canAttendMeetings(Interval[] intervals) {
        if(intervals==null) return false;
        if(intervals.length<2) return true;
        Arrays.sort(intervals, new helperComparator());
        for(int i=0; i<intervals.length-1; i++){
            if(intervals[i].end>intervals[i+1].start) return false;
        }
        return true;

    }

    class helperComparator implements Comparator<Interval>{
        @Override
        public int compare(Interval a, Interval b){
            //null handling
            return a.start-b.start;
        }
    }
}
