package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/range-module/
 * A Range Module is a module that tracks ranges of numbers.
 * Your task is to design and implement the following interfaces in an efficient manner.
 * addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval.
 * Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval [left, right) that are not already tracked.
 * queryRange(int left, int right) Returns true if and only if every real number in the interval [left, right) is currently being tracked.
 * removeRange(int left, int right) Stops tracking every real number currently being tracked in the interval [left, right).
 *
 * Example 1:
 * addRange(10, 20): null
 * removeRange(14, 16): null
 * queryRange(10, 14): true (Every number in [10, 14) is being tracked)
 * queryRange(13, 15): false (Numbers like 14, 14.03, 14.17 in [13, 15) are not being tracked)
 * queryRange(16, 17): true (The number 16 in [16, 17) is still being tracked, despite the remove operation)
 *
 * Note:
 * A half open interval [left, right) denotes all real numbers left <= x < right.
 * 0 < left < right < 10^9 in all calls to addRange, queryRange, removeRange.
 * The total number of calls to addRange in a single test case is at most 1000.
 * The total number of calls to queryRange in a single test case is at most 5000.
 * The total number of calls to removeRange in a single test case is at most 1000.
 */

public class RangeModule {
    TreeMap<Integer, Integer> map;
    public RangeModule() {
        this.map = new TreeMap<>();
    }

    //注意类似于[1, 3) [3, 7)的情况，我们不允许存在，要合并成[1, 7)
    public void addRange(int left, int right) {
        //注意要用Integer，而不是int，因为有可能为null
        Integer pre = this.map.floorKey(left);
        Integer nxt = this.map.floorKey(right);
        //注意check pre nxt是否为null
        if(pre!=null && this.map.get(pre)>=left){
            left = pre;
        }
        if(nxt!=null && this.map.get(nxt)>=right){
            right = this.map.get(nxt);
        }
        this.map.put(left, right);
        //注意left端不能inclusive，否则刚刚加上的interval也被移除了
        this.map.subMap(left, false, right, true).clear();
    }

    public boolean queryRange(int left, int right) {
        Integer pre = this.map.floorKey(left);
        if(pre!=null){
            return this.map.get(pre)>=right;
        }
        return false;
    }

    public void removeRange(int left, int right) {
        Integer pre = this.map.floorKey(left);
        Integer nxt = this.map.floorKey(right);
        //注意：如果这里先处理左边，再处理右边就会出错！比如从[10, 20)中移除[14, 16)，这里pre & left是同一个
        //如果先处理了左边，那么[10, 20)-->[10, 14)，再处理右边的时候，条件就不满足了，有就是把[16, 20)这段就会被丢掉！！！
        if(nxt!=null && this.map.get(nxt)>right){
            this.map.put(right, this.map.get(nxt));
        }
        if(pre!=null && this.map.get(pre)>left){
            this.map.put(pre, left);
        }
        this.map.subMap(left, true, right, false).clear();
    }
}
