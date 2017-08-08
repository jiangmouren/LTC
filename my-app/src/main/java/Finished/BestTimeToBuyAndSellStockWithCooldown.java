/**
 * Question:
 * Say you have an array for which the ith element is the price of a given stock on day i.
 * Design an algorithm to find the maximum profit.
 * You may complete as many transactions as you like (ie, buy one and sell one share of the stock multiple times)
 * with the following restrictions:
 * You may not engage in multiple transactions at the same time (ie, you must sell the stock before you buy again).
 * After you sell your stock, you cannot buy stock on next day. (ie, cooldown 1 day)
 * Example:
 * prices = [1, 2, 3, 0, 2]
 * maxProfit = 3
 * transactions = [buy, sell, cooldown, buy, sell]
 */

/**
 * Analysis:
 * S(n) = Max{B(n-1)+P(n), S(n-1)}; n>=2, possibilities: buy before sell today
 * B(n) = Max{S(n-2)-P(n), N(n)-P(n), B(n-1)}; n>=3, possibilities: sell before buy today, sell today and buy today
 * N(n) = 0;
 * S(0): invalid, S(0)=Integer.MIN_VALUE
 * B(0) = -P(0)
 * S(1) = Max{-P(0)+P(1), Integer.MIN_VALUE} = -P(0)+P(1);
 * B(1) = Max{-P(1), B(0)} = Max{-P(1), -P(0)}
 * S(2) = Max{B(1)+P(2), S(1)}
 * B(2) = Max{S(0)-P(2), -P(2), B(1)} = Max{Integer.MIN_VALUE-P(2), -P(2), Max{-P(1), -P(0)}} "no violation"?
 * Actually there is a big violation here: Integer.MIN_VALUE-P(2) will be an "Overflow" case and will be a "very big" number!!!
 * From n=3, everything is normal.
 * So the best practice should be just take n = {0, 1, 2} as special cases and start recursion from n=3.
 * S(2) = Max{B(1)+P(2), S(1)} = Max{Max{-P(1), -P(0)}+P(2), -P(0)+P(1)} = Max{P(2)-P(1), P(2)-P(0), -P(0)+P(1)}
 * B(2) = Max{-P(2), Max{-P(1), -P(0)}} = Max{-P(2), -P(1), -P(0)};
 * And we need S1, S2, B2 to construct S_next and B_next; and we will shift S1=S2; S2=S_next; B2=B_next;
 */
package Finished;
public class BestTimeToBuyAndSellStockWithCooldown {
    public int bestTransaction(int[] price){
        if(price==null) throw new IllegalArgumentException("Input cannot be null");
        if(price.length==1) return 0;
        if(price.length==2) return Math.max(0, price[1]-price[0]);
        if(price.length==3) return Math.max(0, Math.max(Math.max(price[2]-price[1], price[2]-price[0]), price[1]-price[0]));
        int s1 = price[1]-price[0];
        int s2 = Math.max(Math.max(price[2]-price[1], price[2]-price[0]), price[1]-price[0]);
        int b2 = Math.max(Math.max(-price[2], -price[1]), -price[0]);
        int sNext, bNext;
        for(int i=3; i<price.length; i++){
            sNext = Math.max(b2+price[i], s2);
            bNext = Math.max(-price[i], Math.max(s1-price[i], b2));
            s1 = s2;
            s2 = sNext;
            b2 = bNext;
        }
        return Math.max(0, s2);
    }
}
