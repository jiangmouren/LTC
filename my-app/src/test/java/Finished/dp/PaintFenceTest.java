package Finished.dp;

import Finished.dp.PaintFence;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/29/2017.
 */
public class PaintFenceTest {
    PaintFence objectUnderTest = new PaintFence();
    @Test
    public void paintFence() throws Exception {
        int result1 = objectUnderTest.paintFence1(1, 2);
        int result2 = objectUnderTest.paintFence2(1, 2);
        assertTrue(result1==result2);
        int result3 = objectUnderTest.paintFence1(3, 2);
        int result4 = objectUnderTest.paintFence2(3, 2);
        assertTrue(result3==result4);
        int result5 = objectUnderTest.paintFence1(30, 12);
        int result6 = objectUnderTest.paintFence2(30, 12);
        assertTrue(result5==result6);
    }
}