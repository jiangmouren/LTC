package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/coin-change-2/
 * You are given coins of different denominations and a total amount of money.
 * Write a function to compute the number of combinations that make up that amount.
 * You may assume that you have infinite number of each kind of coin.
 *
 * Example 1:
 * Input: amount = 5, coins = [1, 2, 5]
 * Output: 4
 * Explanation: there are four ways to make up the amount:
 * 5=5
 * 5=2+2+1
 * 5=2+1+1+1
 * 5=1+1+1+1+1
 *
 * Example 2:
 * Input: amount = 3, coins = [2]
 * Output: 0
 * Explanation: the amount of 3 cannot be made up just with coins of 2.
 *
 * Example 3:
 * Input: amount = 10, coins = [10]
 * Output: 1
 *
 *
 * Note:
 * You can assume that
 * 0 <= amount <= 5000
 * 1 <= coin <= 5000
 * the number of coins is less than 500
 * the answer is guaranteed to fit into signed 32-bit integer
 */
public class CoinChange2 {
    /**
     * 而这个题正确的写法竟然是把内外两个for loop颠倒一下！！！
     * 惊呆！！！
     * 具体解释如下：
     * src\main\resources\CoinChange2.docx
     */
    public int change(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;//amount==0的时候，只有一种组合方法，就是一个都不选

        for (int coin : coins) {
            for (int i = coin; i < amount + 1; i++) {
                //dp[i-coin]表达了使用目前所有涵盖的coin所能构成i-coin的组合个数
                //dp[i]在加上dp[i-coin]之前，表达在不使用当前所讨论coin的前提下，所能构成i的组合数
                //然后在加上至少包含一个当前所讨论coin的组合数：所以"i-coin"表达reserve一个当前coin
                //然后dp[i-coin]则涵盖使用和不使用当前coin所能构成i-coin的所有情况
                dp[i] += dp[i - coin];
            }
        }
        return dp[amount];
    }

    /**
     * 这个题乍一看跟CoinChange很想，其实里面有陷阱：
     * 比如对于如下input:
     * 5
     * [1, 2, 5]
     * dp[3] = dp[2] + dp[1] = {{1,1,1}, {1,2}} + {2,1}
     * {1,2}与{2,1}重复！
     * 根本上说，对于dp来说，在根据子问题的解来构造母问题的解的时候，基于各个子问题解所构造出的母问题的解应该是
     * 互相没有交集的，或者说如果出现重叠，能够有效dedupe的，但在这个问题里，下面这种写法无法做到上面两点。
     */
    public int changeWrong(int amount, int[] coins) {
        //dp问题，Coin Change差不多，只不过初值不同，因为表达的意义也不同
        //跟 WordBreakII思路上也差不多，只不过这里不需要具体的解，而是需要个数，所以可以用dp-array标记状态
        int[] dp = new int[amount + 1];
        //initialization
        dp[0] = 1;

        for(int i=1; i<=amount; i++){
            for(int coin : coins){
                if(i-coin>=0){
                    dp[i] += dp[i-coin];
                }
            }
        }
        return dp[amount];
    }

}
