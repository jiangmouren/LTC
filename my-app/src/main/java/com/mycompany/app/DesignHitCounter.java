package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/design-hit-counter/
 * Design a hit counter which counts the number of hits received in the past 5 minutes.
 *
 * Each function accepts a timestamp parameter (in seconds granularity) * and you may assume
 * that calls are being made to the system in chronological order (ie, the timestamp is monotonically increasing).
 * You may assume that the earliest timestamp starts at 1.
 *
 * It is possible that several hits arrive roughly at the same time.
 *
 * Example:
 * HitCounter counter = new HitCounter();
 * // hit at timestamp 1.
 * counter.hit(1);
 * // hit at timestamp 2.
 * counter.hit(2);
 * // hit at timestamp 3.
 * counter.hit(3);
 * // get hits at timestamp 4, should return 3.
 * counter.getHits(4);
 * // hit at timestamp 300.
 * counter.hit(300);
 * // get hits at timestamp 300, should return 4.
 * counter.getHits(300);
 * // get hits at timestamp 301, should return 3.
 * counter.getHits(301);
 * Follow up:
 * What if the number of hits per second could be very large? Does your design scale?
 */
public class DesignHitCounter {
    //思路一：用一个List<Integer>，每次出现一个hit，就把timestamp加进去，然后每次getHits(T)的时候，我们其实需要找的是：
    //         1.小于T的最大的timestamp
    //         2.大于T-300的最小的timestamp
    //针对上面两个需求，可以做两次Binary Search，复杂度是lgN
    //这样做的好处是，首先复杂度比较优秀，然后对于getHits(T)，这里的T可以随机的来，没有只能递增的要求，因为我们保留了所有的历史数据
    //思路二：用一个Queue<Integer>，每来一个新的hit就加在后面，每次一个getHits(T)，就把之前的T-300以外的点全部pop出去，然后余下的queue.size就是hitCount
    //这样的好处是，memory_footprint比较小，缺点主要是不支持随机getHits(T)，然后复杂度上会差一些，但是不会是简单的(n vs lgn)的变化，因为n会变小

    Queue<Integer> queue;
    /** Initialize your data structure here. */
    //注意这里要用queue，而不是list，因为这样在去除前面的entry时，避免O(n)的shift
    public DesignHitCounter() {
        this.queue = new LinkedList<>();
    }

    /** Record a hit.
     @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        this.queue.add(timestamp);
    }

    /** Return the number of hits in the past 5 minutes.
     @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        //这里之所以选择在getHits的时候去除过时entry是因为不管在不在hit的时候做这件事情，这里都必须得做
        //因为getHits跟上一次hit可能间隔很久。
        while(!queue.isEmpty() && timestamp - this.queue.peek()>=300){
            queue.poll();
        }
        return this.queue.size();
    }
}
