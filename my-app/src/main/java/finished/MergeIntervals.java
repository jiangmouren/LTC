package finished;
/**
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

    public static class Interval{
        int start;
        int end;
        Interval() { start = 0; end = 0; }
        Interval(int s, int e) { start = s; end = e; }
    }

    public List<Interval> merge(List<Interval> intervals) {
        List<Interval> list = new ArrayList<>();
        Collections.sort(intervals, new Helper());
        list.add(intervals.get(0));
        int i;
        for(i=1; i<intervals.size(); i++){
            //merge case
            if(list.get(list.size()-1).end>=intervals.get(i).start){
                Interval pre = list.remove(list.size()-1);
                list.add(new Interval(pre.start, intervals.get(i).end));
            }
            else{
                list.add(new Interval(intervals.get(i).start, intervals.get(i).end));
            }
        }
        return list;
    }

    private class Helper implements Comparator<Interval>{
        @Override
        public int compare(Interval o1, Interval o2) {
            return o1.start-o2.start;
        }
    }
}
