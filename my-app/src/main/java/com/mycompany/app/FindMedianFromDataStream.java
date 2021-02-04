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
    //直接想到的有两种解法：
    //一种是用两个Heap，一个MinHeap，一个MaxHeap,各装一半的stream，每次insert的时候check两边的size，决定往哪边放。
    //上面这种解法，insert是O(logN), findMedian是O(1)（不需要pop，只是peek）
    //另外一种解法就是用一个sorted list去存stream
    //每次insert的时候，用Binary Search来确定插入的位置, 但是要在list里insert,复杂度是O(n)
    //所以放弃这种想法
    PriorityQueue<Integer> hiQueue;
    PriorityQueue<Integer> loQueue;
    /** initialize your data structure here. */
    public FindMedianFromDataStream() {
        hiQueue = new PriorityQueue<>(11, (a,b)->a-b);
        loQueue = new PriorityQueue<>(11, (a,b)->b-a);
    }

    public void addNum(int num) {
        //要领有个2个：
        //1. 要保证loQueue里面的数字都比hiQueue里面的小
        //2. 要保证两个Queue的size平衡
        if(loQueue.size()>0 && num<loQueue.peek()){
            loQueue.add(num);
        }
        else{
            hiQueue.add(num);
        }
        if(hiQueue.size()-loQueue.size()>=2){
            int hiMin = hiQueue.poll();
            loQueue.add(hiMin);
        }
        if(loQueue.size()>hiQueue.size()){
            int loMax = loQueue.poll();
            hiQueue.add(loMax);
        }
    }

    public double findMedian() {
        if(hiQueue.size()>loQueue.size()){
            return hiQueue.peek();
        }
        else{
            return (hiQueue.peek()+loQueue.peek())*0.5;
        }
    }
}
