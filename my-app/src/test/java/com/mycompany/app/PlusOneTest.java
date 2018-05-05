package com.mycompany.app;

import com.mycompany.app.PlusOne;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.*;

/**
 * Created by eljian on 6/15/2017.
 */
public class PlusOneTest {
    PlusOne objectUnderTest = new PlusOne();
    int[] nums = {9, 9, 9, 9};
    int[] result = {0, 0, 0, 0, 1};
    @Test
    public void plusOne() throws Exception {
        assertTrue(Arrays.equals(objectUnderTest.plusOne(nums), result));
    }

}