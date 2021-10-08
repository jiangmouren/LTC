package com.mycompany.app.interval;

import java.util.*;

/**
 * https://leetcode.com/problems/interval-list-intersections/
 * You are given two lists of closed intervals, firstList and secondList, where firstList[i] = [starti, endi] and secondList[j] = [startj, endj]. Each list of intervals is pairwise disjoint and in sorted order.
 *
 * Return the intersection of these two interval lists.
 *
 * A closed interval [a, b] (with a < b) denotes the set of real numbers x with a <= x <= b.
 *
 * The intersection of two closed intervals is a set of real numbers that are either empty or represented as a closed interval. For example, the intersection of [1, 3] and [2, 4] is [2, 3].
 *
 * Example 1:
 * Input: firstList = [[0,2],[5,10],[13,23],[24,25]], secondList = [[1,5],[8,12],[15,24],[25,26]]
 * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 *
 * Example 2:
 * Input: firstList = [[1,3],[5,9]], secondList = []
 * Output: []
 *
 * Example 3:
 * Input: firstList = [], secondList = [[4,8],[10,12]]
 * Output: []
 *
 * Example 4:
 * Input: firstList = [[1,7]], secondList = [[3,10]]
 * Output: [[3,7]]
 *
 * Constraints:
 * 0 <= firstList.length, secondList.length <= 1000
 * firstList.length + secondList.length >= 1
 * 0 <= starti < endi <= 109
 * endi < starti+1
 * 0 <= startj < endj <= 109
 * endj < startj+1
 */
public class IntervalListIntersections {
    /**
     * Leetcode这道题的答案还是比较简洁的，直接处理两边的interval，也是借鉴mergeSort中Merge差不多的思路
     * 判断Interval是否Overlap的写法，跟RectangleArea里面判断矩形是否相交一样
     */
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int ptr1 = 0;
        int ptr2 = 0;
        List<int[]> res = new ArrayList<>();
        while(ptr1<A.length && ptr2<B.length){
            // Let's check if A[i] intersects B[j].
            // start - the startpoint of the intersection
            int start = Math.max(A[ptr1][0], B[ptr2][0]);
            int end = Math.min(A[ptr1][1], B[ptr2][1]);
            if(start<=end){
                res.add(new int[]{start, end});
            }
            // Remove the interval with the smallest endpoint
            if(A[ptr1][1]<=B[ptr2][1]){
                ptr1++;
            }
            else{
                ptr2++;
            }
        }
        return res.toArray(new int[res.size()][]);
    }

    /**
     * 我的解法还是使用了我自己独创的经典ListOfEvents解法，唯一的问题就是这个题这么写很长
     * 复杂度上倒是没有问题就是O(M+N),M, N分别是两个list的长度
     */
    public int[][] intervalIntersectionSln2(int[][] firstList, int[][] secondList) {
        //先生成两个sorted event list, 本身每个list内部的interval是disjoint的
        //然后merge它们，变成一个sorted event list
        //然后run through，如果并行度从1->2，那么生成一个新的start event，如果从2->1，那么生成一个end event
        //最后成对的把生成的event构造成list of intervals
        int l1 = firstList.length;
        int l2 = secondList.length;
        int[][] eventList1 = new int[l1*2][2];
        int[][] eventList2 = new int[l2*2][2];
        populate(firstList, eventList1);
        populate(secondList, eventList2);
        int[][] eventList = merge(eventList1, eventList2);
        List<int[]> res = new ArrayList<>();
        int cnt = 0;
        for(int[] event : eventList){
            if(event[1]==0){
                cnt++;
                if(cnt==2){
                    res.add(event);
                }
            }
            else{
                cnt--;
                if(cnt==1){
                    res.add(event);
                }
            }
        }

        int[][] interS = new int[res.size()/2][2];
        int ptr = 0;
        for(int i=0; i<res.size(); i++){
            interS[ptr][0] = res.get(i)[0];
            i++;
            interS[ptr][1] = res.get(i)[0];
            ptr++;
        }

        return interS;
    }

    private void populate(int[][] list, int[][] eventList){
        int ptr = 0;
        for(int i=0; i<list.length; i++){
            eventList[ptr][0] = list[i][0];
            eventList[ptr][1] = 0;//0: start, 1: end
            ptr++;
            eventList[ptr][0] = list[i][1];
            eventList[ptr][1] = 1;
            ptr++;
        }
        return;
    }

    private int[][] merge(int[][] list1, int[][] list2){
        int l1 = list1.length;
        int l2 = list2.length;
        int[][] res = new int[l1+l2][2];
        int ptr1 = 0;
        int ptr2 = 0;
        int ptr3 = 0;
        while(ptr1<l1 && ptr2<l2){
            if(list1[ptr1][0]<list2[ptr2][0] || list1[ptr1][0]==list2[ptr2][0] && list1[ptr1][1]==0){
                res[ptr3][0] = list1[ptr1][0];
                res[ptr3][1] = list1[ptr1][1];
                ptr1++;
            }
            else{
                res[ptr3][0] = list2[ptr2][0];
                res[ptr3][1] = list2[ptr2][1];
                ptr2++;
            }
            ptr3++;
        }

        while(ptr1<l1){
            res[ptr3][0] = list1[ptr1][0];
            res[ptr3][1] = list1[ptr1][1];
            ptr3++;
            ptr1++;
        }
        while(ptr2<l2){
            res[ptr3][0] = list2[ptr2][0];
            res[ptr3][1] = list2[ptr2][1];
            ptr3++;
            ptr2++;
        }

        return res;
    }
}
