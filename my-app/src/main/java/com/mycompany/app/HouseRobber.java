package com.mycompany.app;
/**
 * Question:
 * You are a professional robber planning to rob houses along a street.
 * Each house has a certain amount of money stashed,
 * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected
 * and it will automatically contact the police if two adjacent houses were broken into on the same night.
 * Given a list of non-negative integers representing the amount of money of each house,
 * determine the maximum amount of money you can rob tonight without alerting the police.
 */

/**
 * Analysis:
 * Typical DP problem. Like climbing stairs, like buy and sell stock, you have a consecutive sequence of decisions
 * to make. And the decision at every step, is constrained by what happened before.
 * The approach for this kind of problem is to lay out all possibilities for one step and then back track the recursion following
 * the constraints requirements.
 * For this specific problem:
 * f(n) = Max{A(n), B(n)}, where A(n) is max profit if house(n) robbed, B(n) is max profit if house(n) not robbed.
 * A(n) = B(n-1) + M(n), where M(n) is the amount of money in house(n), n>=1;
 * B(n) = Max{A(n-1), B(n-1)}, n>=1;
 * A(0) = M(0), B(0) = 0;
 *
 * A(1) = B(0) + M(0) = M(0), valid;
 * B(1) = Max{A(0), B(0)} = M(0), valid;
 * So we can start recursion from n==1.
 */

public class HouseRobber{
    public int maxRobber(int[] money){
        //special cases
        if(money==null) throw new IllegalArgumentException("Input cannot be null");
        if(money.length==1) return money[0];

        int a=money[0], b=0;
        int aNext, bNext;
        for(int i=1; i<money.length; i++){
            aNext = b + money[i];
            bNext = Math.max(a, b);
            a = aNext;
            b = bNext;
        }
        return Math.max(a, b);
    }
}
