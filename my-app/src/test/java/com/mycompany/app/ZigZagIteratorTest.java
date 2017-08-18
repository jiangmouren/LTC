package com.mycompany.app;

import org.junit.Test;
import java.util.*;
import static org.junit.Assert.*;

/**
 * Created by eljian on 8/17/2017.
 */
public class ZigZagIteratorTest {
    List<int[]> list1 = new ArrayList<int[]>();
    List<int[]> list2 = new ArrayList<int[]>();
    int[] tmp1 = {1, 2, 3};
    int[] tmp2 = {4, 5, 6, 7};
    int[] tmp3 = {8, 9};
    {
        list1.add(tmp1);
        list1.add(tmp2);
        list1.add(tmp3);
        list2.add(tmp2);
    }
    @Test
    public void test() throws Exception {
        ZigZagIterator objectUnderTest1 = new ZigZagIterator(list1);
        ZigZagIterator objectUnderTest2 = new ZigZagIterator(list2);
        while(objectUnderTest1.hasNext()){
            System.out.print(objectUnderTest1.next());
        }
        System.out.println();
        while(objectUnderTest2.hasNext()){
            System.out.print(objectUnderTest2.next());
        }
    }
}