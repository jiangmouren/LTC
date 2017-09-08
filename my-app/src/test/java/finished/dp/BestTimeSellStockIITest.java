package finished.dp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 6/8/2017.
 */
public class BestTimeSellStockIITest {
    BestTimeSellStockII objectUnderTest = new BestTimeSellStockII();
    int[] prices = {1, 2, 4, 100, 15};
    @Test
    public void maxProfit1() throws Exception {
        int result = objectUnderTest.maxProfit1(prices);
        System.out.println(result);
        assertTrue(result==(100-4+4-2+2-1));
    }

    @Test
    public void maxProfit2() throws Exception {
        int result = objectUnderTest.maxProfit2(prices);
        System.out.println(result);
        assertTrue(result==(100-4+4-2+2-1));
    }
}