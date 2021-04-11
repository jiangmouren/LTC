/**
 * Question:
 * Given an array of meeting time intervals consisting of start and end times [[s1,e1],[s2,e2],...] (si < ei),
 * find the minimum number of conference rooms required.
 * For example,
 * Given [[0, 30],[5, 10],[15, 20]],
 * return 2.
 */

/**
 * Analysis:
 * This one essentially is asking what is the maximum parallelism of all the meetings?
 * This is also similar to using streaming to count the number of cars in a garage:
 * Whenever there ia a car in, count++; whenever there is a car out, count--.
 * I don't care which car is in or which car is out.
 * In this case, whenever there is a start, count++; whenever there is an end, count--.
 * I don't care if it is meeting_1 overlaps with meeting_3 or meeting_2 overlaps with meeting_3.
 *
 */
package com.mycompany.app;

import java.util.*;
public class MeetingRoomsII {
    //这个题要安排会议，很显然的需要先根据会议开始的时间对会议进行排序，毕竟得按照开始时间去安排。
    //这个问题的思路其实很简单，就是来一个会议，我看看有没有会议结束了，有就不用再allocate新的会议室
    //没有就需要再allocate新的会议室，然后看再整个过程中最多的时候有几个会议室同时被占用。
    //问题是：用什么data structure来描述这样一个过程？

    /**
     * MinHeap应该是最明显的option，因为当一个新的会议来的时候，可以跟之前最早结束会议的结束时间做个比较。
     * 如果已经结束了，就先pop出来，再把新的会议加进去；如果没结束，就直接加进去。
     * 在这个过程中，keep track of the size of the heap，出现的最大值就是最少需要的会议室的数目。
     */
    public int minMeetingRoomsHeap(int[][] intervals) {
        Arrays.sort(intervals, (a,b)->a[0]-b[0]);
        PriorityQueue<int[]> queue = new PriorityQueue<>(10, (a,b)->a[1]-b[1]);
        int max = 0;
        for(int[] interval : intervals){
            if(queue.size()==0 || queue.peek()[1]>interval[0]){
                queue.add(interval);
                max = Math.max(max, queue.size());
            }
            else{
                queue.poll();
                queue.add(interval);
            }
        }
        return max;
    }

    /**
     * 再抽象一点的看这个问题，我们不关心会议的过程，只关心会议的开始和结束。
     * 可以把所有的开始和结束，在时间轴上排开，遇到一个开始cnt++，遇到一个结束cnt--，在这个过程中cnt的最大值就是要找的。
     * 类似的也可以用这个办法来找一个string里面的括号最多出现到了几层。
     * 这个问题还可以有一个变种，去实际生成会议室安排表。
     * 这种情况下，只需要在PriorityQueue解法的基础上稍作改动即可解。
     * 往PriorityQueue不直接放meeting，而是用一个wrapper class把meeting和一个list一起放进去。
     * wrapper class里面的list指向的是一个会议室，所以每次新的meeting来的时候，先看有没有要结束的。
     * 如果有，就reuse刚结束的list，同时把自己加紧那个list里面，如果没有，就新create一个list，把自己加进去，
     * 也把新create的list加入result -> List<List<int[]>>里面去.
     */
    public int minMeetingRoomsEvents(int[][] intervals) {
        List<int[]> list = new ArrayList<>();
        for(int[] interval : intervals){
            list.add(new int[]{interval[0], 0});//0: start
            list.add(new int[]{interval[1], 1});//1: end
        }
        Collections.sort(list, (a, b)->{
            int cmp = a[0]-b[0];
            if(cmp>0){
                return 1;
            }
            else if(cmp<0){
                return -1;
            }
            else{
                return a[1]==0 ? 1 : -1;
            }
        });
        int cnt = 0;
        int max = 0;
        for(int[] arr : list){
            if(arr[1]==0){
                cnt++;
            }
            else{
                cnt--;
            }
            max = Math.max(max, cnt);
        }
        return max;
    }
}
