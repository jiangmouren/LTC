package com.mycompany.app;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times).
 * However, you may not engage in multiple transactions at the same time
 * (ie, you must sell the stock before you buy again).
 */

/**
 * Analysis:
 * This is a very good DP example.
 * Need to understand the problem description:
 * 1st. At any given time, only 1 share at hand, multiple transactions per day is Okay,
 * but sell+buy makes sense, while buy+sell doesn't make sense, though valid operation.
 * 2nd. Only one share to trade at a time.
 * Quick notes about DP:
 * The overall approach is to split the original problem into recursive sub-problems,
 * if the original problem itself is not recursive.
 * Get the recursive function, and figure out the edge cases(be careful and patient when doing this).
 *
 * The art and tricks are how you pick sub-problems.
 * TODO: Review previous DP notes.
 *
 * In this problem, at every day you need to decide: buy, sell or nothing, until the last day.
 * Among all the different paths, which one gives the the best result is the question.
 * Backtracking is the naive way.
 * DP is a more educated way.
 * S(n): the max profit for the nth day, with the latest valid operation is "Sell"
 * B(n): the max profit for the nth day, with the latest valid operation is "Buy"
 * N(n): the max profit for the nth day, with the latest valid operation is "Nothing"
 * P(n): the stock price on nth day.
 * S(n) = Max{B(n-1)+P(n), S(n-1)}; n>=2, possibilities: buy before sell today
 * B(n) = Max{S(n-1)-P(n), S(n)-P(n), B(n-1)}; n>=2, possibilities: sell before buy today, sell today and buy today
 * N(n) = 0;
 * The target function: Max{S(n), B(n), N(n)} = Max{S(n), N(n)}
 * S(0): invalid; S(1): P(1)-P(0);
 * B(0): -P(0); B(1): -P(1)
 *
 * Further Analysis:
 * If looking at the following example:
 * prices = {1, 2, 4, 100, 15};
 * Because we cannot hold, so every transaction(buy+sell) is an independent transaction.
 * So this is actually a Divide and Conquer problem:
 * whenever there is profit margin, do the transaction, sum up the profit.
 *
 * Normally if a DP problem can be simplified into a DC problem, it will be easier to handle.
 */
public class BestTimeSellStockII {
    //DP
    public int maxProfit1(int[] prices) {
        int l = prices.length;
        if(l==1)return 0;
        int B0=-prices[0], S0=0, S1=Math.max(B0+prices[1], S0), B1=Math.max(S0-prices[1], S1-prices[1]);
        if(l==2) return Math.max(S1, 0);
        int currentS = S1;
        int currentB = B1;
        int nextS, nextB;
        for(int i=2; i<l; i++){
            nextS=Math.max(currentB+prices[i], currentS);
            nextB=Math.max(currentS-prices[i], currentB);
            currentS = nextS;
            currentB = nextB;
        }
        return Math.max(currentS, 0);
    }

    //DC
    public int maxProfit2(int[] prices) {
        if(prices.length<2) return 0;
        int profit = 0;
        for(int i=0; i<prices.length-1; i++){
            if(prices[i]<prices[i+1])
                profit+=(prices[i+1]-prices[i]);
        }
        return profit;
    }
}
