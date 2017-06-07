package com.mycompany.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/6/2017.
 */
public class ClimbingStairsTest {
    ClimbingStairs objectUnderTest = new ClimbingStairs();
    @Test
    public void climbStairs() throws Exception {
        assertTrue(objectUnderTest.climbStairs(1)==1);
        assertTrue(objectUnderTest.climbStairs(2)==2);
        assertTrue(objectUnderTest.climbStairs(3)==3);
        assertTrue(objectUnderTest.climbStairs(4)==5);
        assertTrue(objectUnderTest.climbStairs(5)==8);
        assertTrue(objectUnderTest.climbStairs(6)==13);
    }

    @Test
    public void climbStairsRecur() throws Exception {
        assertTrue(objectUnderTest.climbStairsRecur(1)==1);
        assertTrue(objectUnderTest.climbStairsRecur(2)==2);
        assertTrue(objectUnderTest.climbStairsRecur(3)==3);
        assertTrue(objectUnderTest.climbStairsRecur(4)==5);
        assertTrue(objectUnderTest.climbStairsRecur(5)==8);
        assertTrue(objectUnderTest.climbStairsRecur(6)==13);
    }
}