package com.mycompany.app;

import com.mycompany.app.graph.HappyNumber;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/7/2017.
 */
public class HappyNumberTest {
    HappyNumber objectUnderTest = new HappyNumber();
    @Test
    public void isHappy() throws Exception {
        assertTrue(objectUnderTest.isHappy(19));
        assertFalse(objectUnderTest.isHappy(18));
    }

    @Test
    public void isHappyLoop() throws Exception {
        assertTrue(objectUnderTest.isHappyLoop(19));
        assertFalse(objectUnderTest.isHappyLoop(18));
    }

}