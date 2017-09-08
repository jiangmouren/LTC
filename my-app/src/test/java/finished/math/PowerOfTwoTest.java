package finished.math;

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
        assertTrue(objectUnderTest.isPowerOfTwoLoop(1));
        assertTrue(objectUnderTest.isPowerOfTwoLoop(2));
        assertTrue(objectUnderTest.isPowerOfTwoLoop(4));
        assertFalse(objectUnderTest.isPowerOfTwoLoop(3));
        assertFalse(objectUnderTest.isPowerOfTwoLoop(5));
        assertFalse(objectUnderTest.isPowerOfTwoLoop(14));
    }
}