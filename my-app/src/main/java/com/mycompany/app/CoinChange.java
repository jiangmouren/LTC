package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/coin-change/
 * You are given coins of different denominations and a total amount of money amount.
 * Write a function to compute the fewest number of coins that you need to make up that amount.
 * If that amount of money cannot be made up by any combination of the coins, return -1.
 * You may assume that you have an infinite number of each kind of coin.
 *
 * Example 1:
 * Input: coins = [1,2,5], amount = 11
 * Output: 3
 * Explanation: 11 = 5 + 5 + 1
 *
 * Example 2:
 * Input: coins = [2], amount = 3
 * Output: -1
 *
 * Example 3:
 * Input: coins = [1], amount = 0
 * Output: 0
 *
 * Example 4:
 * Input: coins = [1], amount = 1
 * Output: 1
 *
 * Example 5:
 * Input: coins = [1], amount = 2
 * Output: 2
 *
 * Constraints:
 * 1 <= coins.length <= 12
 * 1 <= coins[i] <= 231 - 1
 * 0 <= amount <= 104
 */

public class CoinChange{
    public static void main(String[] args){
        CoinChange instance = new CoinChange();
        int[] coins = {1, 2, 5};
        System.out.println(instance.coinChange(coins, 70));
    }

    /**
     * 照理来说，这应该是一道很经典的切香肠，或者backpack，类的DP问题，但是我却第一个想到的是遍历，惭愧！
     */
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount+1];
        int minCoin = Integer.MAX_VALUE;
        for(int coin : coins){
            minCoin = Math.min(minCoin, coin);
        }
        //set up initial values
        dp[0] = 0;
        for(int i=1; i<minCoin && i<dp.length; i++){//注意：不要忘记i<dp.length !
            dp[i] = Integer.MAX_VALUE;
        }
        //calculate dp
        for(int i=minCoin; i<=amount; i++){
            dp[i] = Integer.MAX_VALUE;
            for(int coin : coins){
                int j = i-coin;
                if(j>=0){
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
            if(dp[i]!=Integer.MAX_VALUE){
                dp[i] += 1;
            }
        }
        return dp[amount]==Integer.MAX_VALUE ? -1 : dp[amount];
    }
}