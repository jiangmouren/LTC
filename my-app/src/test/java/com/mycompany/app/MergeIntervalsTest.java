package com.mycompany.app;

import com.mycompany.app.MergeIntervals;
import org.junit.Test;
import com.mycompany.app.MergeIntervals.*;
import java.util.*;

/**
 * Created by eljian on 9/19/2017.
 */
public class MergeIntervalsTest {
    MergeIntervals obj = new MergeIntervals();
    List<Interval> list = new ArrayList<>();
    {
        list.add(new Interval(1, 3));
        list.add(new Interval(2, 6));
        list.add(new Interval(8, 10));
        list.add(new Interval(15, 18));
    }
    @Test
    public void merge() throws Exception {
        printIntervals(obj.merge(list));
    }

    private void printIntervals(List<Interval> list){
        for(Interval token : list){
            System.out.print("start: " + token.start + ", " + "end: " + token.end + ";");
            System.out.println();
        }
    }

}