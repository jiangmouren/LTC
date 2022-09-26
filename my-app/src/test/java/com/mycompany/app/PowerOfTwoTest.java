package com.mycompany.app;

import com.mycompany.app.PowerOfTwo;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/6/2017.
 */
public class PowerOfTwoTest {
    PowerOfTwo objectUnderTest = new PowerOfTwo();
    @Test
    public void isPowerOfTwo() throws Exception {
        assertTrue(objectUnderTest.isPowerOfTwo(1));
        assertTrue(objectUnderTest.isPowerOfTwo(2));
        assertTrue(objectUnderTest.isPowerOfTwo(4));
        assertFalse(objectUnderTest.isPowerOfTwo(3));
        assertFalse(objectUnderTest.isPowerOfTwo(5));
        assertFalse(objectUnderTest.isPowerOfTwo(14));
    }

    @Test
    public void isPowerOfTwoLoop() throws Exception {
        assertTrue(objectUnderTest.isPowerOfTwo(1));
        assertTrue(objectUnderTest.isPowerOfTwo(2));
        assertTrue(objectUnderTest.isPowerOfTwo(4));
        assertFalse(objectUnderTest.isPowerOfTwo(3));
        assertFalse(objectUnderTest.isPowerOfTwo(5));
        assertFalse(objectUnderTest.isPowerOfTwo(14));
    }
}