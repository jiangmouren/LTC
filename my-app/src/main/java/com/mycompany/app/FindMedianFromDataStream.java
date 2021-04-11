package com.mycompany.app;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 * Median is the middle value in an ordered integer list. If the size of the list is even, there is no middle value. So the median is the mean of the two middle value.
 *
 * For example,
 * [2,3,4], the median is 3
 * [2,3], the median is (2 + 3) / 2 = 2.5
 *
 * Design a data structure that supports the following two operations:
 * void addNum(int num) - Add a integer number from the data stream to the data structure.
 * double findMedian() - Return the median of all elements so far.
 *
 * Example:
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 *
 *
 * Follow up:
 * If all integer numbers from the stream are between 0 and 100, how would you optimize it?
 * 这种情况可是使用“Counting Sort”: 用一个array记录0-100每个数字的出现的count，同时记录一个总count.
 * 每次addNum()的时候，update相应数字的count，和总的count。
 * 当findMedian()的时候，根据总的count，就可以知道median是第几个，比如说是第20个。
 * 那么久可以从0开始依次往上找，因为知道每个数字出现的次数，所以就可以知道第20个是哪个数字。
 * 因为一共就101个数字，所以最多找101次，所以complexity固定的是o(1).
 *
 * If 99% of all integer numbers from the stream are between 0 and 100, how would you optimize it?
 * 在上面的解法上，做如下的改动。
 * 把记录count的array长度加2，也去记录小于0和大于100的数字出现的次数。
 * 同时对于小于0和大于100的出现数字本身各自存在一个list里（不同于0-100的数字我们只记录次数）
 * 当findMedian的时候，可以根据总count和countArray判断出median是落在那个区间( ,0) || [0,100] || (100, )
 * 以及是所在区间内的第几个。
 * 对于[0,100]的情况，处理方法同上面一样。
 * 对于( ,0) || (100, )的情况，因为数字较少(1%)，我们可以直接sort.
 */

import java.util.*;

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
public class FindMedianFromDataStream{
    //核心是用两个PriorityQueue把data stream拆成上下两部分
    //分别用一个minPriorityQueue和一个maxPriorityQueue
    //这种解法，insert是O(logN), findMedian是O(1)（不需要pop，只是peek）
    PriorityQueue<Integer> hiQ;//minQueue
    PriorityQueue<Integer> loQ;//maxQueue
    /** initialize your data structure here. */
    public FindMedianFromDataStream() {
        this.hiQ = new PriorityQueue<>(11, (a,b)->a-b);
        this.loQ = new PriorityQueue<>(11, (a,b)->b-a);
    }

    public void addNum(int num) {
        //核心有两条：1. 保证hiQ的size不小于loQ；2. 要维持两边size的平衡
        if(loQ.size()>0 && num<loQ.peek()){//只有确定要进lo的才进lo
            loQ.add(num);
        }
        else{//可进lo,也可进hi的,默认进hi，后面再来平衡size
            hiQ.add(num);
        }

        //balance size
        if(hiQ.size()-loQ.size()>1){
            int hiMin = hiQ.poll();
            loQ.add(hiMin);
        }
        if(loQ.size()>hiQ.size()){
            int loMax = loQ.poll();
            hiQ.add(loMax);
        }
    }

    public double findMedian() {
        if(hiQ.size()>loQ.size()){
            return hiQ.peek();
        }
        else{
            return (hiQ.peek()+loQ.peek())*0.5;
        }
    }
}
