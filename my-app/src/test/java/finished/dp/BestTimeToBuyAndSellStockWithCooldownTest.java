package finished.dp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 7/20/2017.
 */
public class BestTimeToBuyAndSellStockWithCooldownTest {
    BestTimeToBuyAndSellStockWithCooldown objectUnderTest = new BestTimeToBuyAndSellStockWithCooldown();
    int[] price = {1, 2, 3, 0, 2};
    @Test
    public void bestTransaction() throws Exception {
        int result = objectUnderTest.bestTransaction(price);
        assertEquals(3, result);
    }
}