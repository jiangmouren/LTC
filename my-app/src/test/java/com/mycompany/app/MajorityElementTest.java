package com.mycompany.app;

import com.mycompany.app.majorityVote.MajorityElement;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/7/2017.
 */
public class MajorityElementTest {
    MajorityElement objectUnderTest = new MajorityElement();

    @Test
    public void majorityElement() throws Exception {
        int[] nums = {1, 2, 3, 4, 2, 3, 2, 2, 2};
        int res = objectUnderTest.majorityElement(nums);
        assertTrue(res==2);
    }

}