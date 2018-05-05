package com.mycompany.app;

import com.mycompany.app.ThreeSumSmaller;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/14/2017.
 */
public class ThreeSumSmallerTest {
    ThreeSumSmaller objectUnderTest = new ThreeSumSmaller();
    int[] nums = {-2, 0, 1, 3};
    int target = 2;
    @Test
    public void threeSumSmaller() throws Exception {
        System.out.println(objectUnderTest.ThreeSumSmaller(nums, target));
        assertTrue(objectUnderTest.ThreeSumSmaller(nums, target)==2);
    }

}