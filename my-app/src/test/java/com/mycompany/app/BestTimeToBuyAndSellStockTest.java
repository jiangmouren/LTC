package com.mycompany.app;

import com.mycompany.app.BestTimeToBuyAndSellStock;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by eljian on 9/30/2017.
 */
public class BestTimeToBuyAndSellStockTest {
    BestTimeToBuyAndSellStock obj = new BestTimeToBuyAndSellStock();
    int[] prices1 = {7, 1, 5, 3, 6, 4};
    int[] prices2 = {7, 6, 4, 3, 1};
    @Test
    public void maxProfit() throws Exception {
        assertEquals(obj.maxProfit(prices1), 5);
        assertEquals(obj.maxProfit(prices2), 0);
    }
}