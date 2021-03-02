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
     * 这题目用DFS解，会timeout!!!
     */
    public int coinChange(int[] coins, int amount) {
        Arrays.sort(coins);
        int h = amount/coins[0];
        h = h*coins[0]<amount ? h+1 : h;
        int l = amount/coins[coins.length-1];
        l = l*coins[coins.length-1]<amount ? l+1 : l;
        System.out.println("h: "+h);
        System.out.println("l: "+l);
        Map<int[], Boolean> map = new HashMap<>();

        for(int i=l; i<=h; i++){
            if(dfs(coins, amount, 0, 0, i, map)){
                return i;
            }
        }
        return -1;
    }

    private boolean dfs(int[] coins, int amount, int pos, int sum, int cnt, Map<int[], Boolean> map){
        //termination condition
        if(pos>=cnt){
            return sum==amount;
        }
        int[] key = {amount-sum, cnt-pos};
        if(map.containsKey(key)){
            return map.get(key);
        }

        for(int coin : coins){
            if(dfs(coins, amount, pos+1, sum+coin, cnt, map)){
                map.put(key, true);
                return true;
            }
        }
        map.put(key, false);
        return false;
    }

    /**
     * 照理来说，这应该是一道很经典的切香肠，或者backpack，类的DP问题，但是我却第一个想到的是遍历，惭愧！
     */
    public int coinChangeDp(int[] coins, int amount) {
        //long length = amount + 1;
        int[] dp = new int[amount + 1];
        System.out.println(dp.length);
        //initialization
        int minC = Integer.MAX_VALUE;
        for(int coin : coins){
            minC = Math.min(minC, coin);
        }
        dp[0] = 0;
        for(int i=1; i<minC && i<dp.length; i++){
            dp[i] = -1;
        }

        for(int i=minC; i<=amount; i++){
            dp[i] = Integer.MAX_VALUE;
            for(int coin : coins){
                if(i-coin>=0 && dp[i-coin]!=-1){
                    dp[i] = Math.min(dp[i], dp[i-coin]+1);
                }
            }
            if(dp[i]==Integer.MAX_VALUE){
                dp[i] = -1;
            }
        }

        return dp[amount];
    }

}