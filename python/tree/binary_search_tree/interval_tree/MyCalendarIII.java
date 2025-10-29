package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * https://leetcode.com/problems/my-calendar-iii/
 * A k-booking happens when k events have some non-empty intersection (i.e., there is some time that is common to all k events.)
 * You are given some events [start, end), after each given event, return an integer k representing the maximum k-booking between all the previous events.
 * Implement the MyCalendarThree class:
 * MyCalendarThree() Initializes the object.
 * int book(int start, int end) Returns an integer k representing the largest integer such that there exists a k-booking in the calendar.
 *
 * Example 1:
 * Input
 * ["MyCalendarThree", "book", "book", "book", "book", "book", "book"]
 * [[], [10, 20], [50, 60], [10, 40], [5, 15], [5, 10], [25, 55]]
 * Output
 * [null, 1, 1, 2, 3, 3, 3]
 * Explanation
 * MyCalendarThree myCalendarThree = new MyCalendarThree();
 * myCalendarThree.book(10, 20); // return 1, The first event can be booked and is disjoint, so the maximum k-booking is a 1-booking.
 * myCalendarThree.book(50, 60); // return 1, The second event can be booked and is disjoint, so the maximum k-booking is a 1-booking.
 * myCalendarThree.book(10, 40); // return 2, The third event [10, 40) intersects the first event, and the maximum k-booking is a 2-booking.
 * myCalendarThree.book(5, 15); // return 3, The remaining events cause the maximum K-booking to be only a 3-booking.
 * myCalendarThree.book(5, 10); // return 3
 * myCalendarThree.book(25, 55); // return 3
 *
 * Constraints:
 * 0 <= start < end <= 109
 * At most 400 calls will be made to book.
 */

public class MyCalendarIII {
    //list[0]: time; list[1]: 0 for start, 1 for end
    Map<List<Integer>, Integer> map;

    public MyCalendarIII() {
        this.map = new TreeMap<>((a, b)->{
            int cmp = a.get(0) - b.get(0);
            //这里一定要加上a.get(1)!=b.get(1)，否则cmp就没有等于0的时候，导致后面的map.containsKey()永远是false!!!
            //因为TreeMap不是基于hash的，本质是个Tree，是基于equals()的
            if(cmp==0 && a.get(1)!=b.get(1)){
                cmp = a.get(1)==0 ? 1 : -1;
            }
            return cmp;
        });
    }

    public int book(int start, int end) {
        List<Integer> startEvent = new ArrayList<>();
        startEvent.add(start);
        startEvent.add(0);
        List<Integer> endEvent = new ArrayList<>();
        endEvent.add(end);
        endEvent.add(1);
        if(!this.map.containsKey(startEvent)){
            this.map.put(startEvent, 0);
        }
        if(!this.map.containsKey(endEvent)){
            this.map.put(endEvent, 0);
        }
        this.map.put(startEvent, this.map.get(startEvent)+1);
        this.map.put(endEvent, this.map.get(endEvent)+1);

        int cnt = 0;
        int max = 0;
        for(List<Integer> event : this.map.keySet()){
            cnt = event.get(1)==0 ? cnt+this.map.get(event) : cnt-this.map.get(event);
            max = Math.max(max, cnt);
        }
        return max;
    }
}
