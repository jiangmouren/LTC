package finished.graph.backtracking;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/24/2017.
 */
public class FlipGameIITest {
    FlipGameII objectUnderTest = new FlipGameII();
    @Test
    public void guaranteeWin() throws Exception {
        assertTrue(objectUnderTest.guaranteeWin("++"));
        assertTrue(objectUnderTest.guaranteeWin("+++"));
        assertTrue(objectUnderTest.guaranteeWin("++++"));
        assertTrue(objectUnderTest.guaranteeWin("++++-++"));
        assertFalse(objectUnderTest.guaranteeWin("++-++"));
        assertFalse(objectUnderTest.guaranteeWin("++++-++++"));
    }

}