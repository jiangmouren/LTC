package finished.dp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jiangmouren on 6/18/17.
 */
public class PaintHouseTest {
    PaintHouse objectUnderTest = new PaintHouse();
    int[][] costs = {
            {1, 3, 2},
            {4, 1, 2},
            {2, 2, 2},
            {3, 5, 4}
    };
    @Test
    public void minCost() throws Exception {
        assertTrue(objectUnderTest.minCost(costs)==7);
    }

}