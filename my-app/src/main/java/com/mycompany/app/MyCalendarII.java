package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/my-calendar-ii/
 * Implement a MyCalendarTwo class to store your events. A new event can be added if adding the event will not cause a triple booking.
 * Your class will have one method, book(int start, int end). Formally, this represents a booking on the half open interval [start, end), the range of real numbers x such that start <= x < end.
 * A triple booking happens when three events have some non-empty intersection (ie., there is some time that is common to all 3 events.)
 * For each call to the method MyCalendar.book, return true if the event can be added to the calendar successfully without causing a triple booking. Otherwise, return false and do not add the event to the calendar.
 * Your class will be called like this: MyCalendar cal = new MyCalendar(); MyCalendar.book(start, end)
 *
 * Example 1:
 * MyCalendar();
 * MyCalendar.book(10, 20); // returns true
 * MyCalendar.book(50, 60); // returns true
 * MyCalendar.book(10, 40); // returns true
 * MyCalendar.book(5, 15); // returns false
 * MyCalendar.book(5, 10); // returns true
 * MyCalendar.book(25, 55); // returns true
 * Explanation:
 * The first two events can be booked.  The third event can be double booked.
 * The fourth event (5, 15) can't be booked, because it would result in a triple booking.
 * The fifth event (5, 10) can be booked, as it does not use time 10 which is already double booked.
 * The sixth event (25, 55) can be booked, as the time in [25, 40) will be double booked with the third event;
 * the time [40, 50) will be single booked, and the time [50, 55) will be double booked with the second event.
 *
 * Note:
 * The number of calls to MyCalendar.book per test case will be at most 1000.
 * In calls to MyCalendar.book(start, end), start and end are integers in the range [0, 10^9].
 */

public class MyCalendarII {
    //list[0]: time; list[1]: 0 for start, 1 for end
    Map<List<Integer>, Integer> map;

    public MyCalendarII() {
        this.map = new TreeMap<>((a, b)->{
            int cmp = a.get(0) - b.get(0);
            //这里一定要加上a.get(1)!=b.get(1)，否则cmp就没有等于0的时候，导致后面的map.containsKey()永远是false!!!
            if(cmp==0 && a.get(1)!=b.get(1)){
                cmp = a.get(1)==0 ? 1 : -1;
            }
            return cmp;
        });
    }

    public static void main(String[] args){
        MyCalendarII instance = new MyCalendarII();
        System.out.println(instance.book(10, 20));
        System.out.println(instance.book(50, 60));
        System.out.println(instance.book(10, 40));
        System.out.println(instance.book(5, 15));
        System.out.println(instance.book(5, 10));
        System.out.println(instance.book(25, 55));
    }

    public boolean book(int start, int end) {
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
        for(List<Integer> event : this.map.keySet()){
            cnt = event.get(1)==0 ? cnt+this.map.get(event) : cnt-this.map.get(event);
            if(cnt==3){
                this.map.put(startEvent, this.map.get(startEvent)-1);
                this.map.put(endEvent, this.map.get(endEvent)-1);
                if(this.map.get(startEvent)==0){
                    this.map.remove(startEvent);
                }
                if(this.map.get(endEvent)==0){
                    this.map.remove(endEvent);
                }
                return false;
            }
        }
        return true;
    }
}
