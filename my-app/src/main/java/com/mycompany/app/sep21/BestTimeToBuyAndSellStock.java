package com.mycompany.app.sep21;

/**
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * If you were only permitted to complete at most one transaction (ie, buy one and sell one share of the stock),
 * design an algorithm to find the maximum profit.
 *
 * Example 1:
 * Input: [7, 1, 5, 3, 6, 4]
 * Output: 5
 * max. difference = 6-1 = 5 (not 7-1 = 6, as selling price needs to be larger than buying price)
 *
 * Example 2:
 * Input: [7, 6, 4, 3, 1]
 * Output: 0
 * In this case, no transaction is done, i.e. max profit = 0.
 */

/**
 * Analysis:
 * The naive way would be to examine all substrings and see which will give the maximum value.
 * The problem with this naive approach is that we are not "selective".
 * By being "selective", I mean we should only care about the current min and max.
 * Basically if it is not the current min/max, I do not care about the diff it has with other nodes.
 */
class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        int min = prices[0];
        int res = 0;
        for(int i=0; i<prices.length; i++){
            if(prices[i]<min){
                min = prices[i];
            }
            res = Math.max(res, prices[i]-min);
        }
        return res;
    }
}
