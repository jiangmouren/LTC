package Finished.dp;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * You are climbing a stair case. It takes n steps to reach to the top.
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * Note: Given n will be a positive integer.
 */

/**
 * Analysis:
 * Simple and Typical DP.
 * The question itself is a "Recursion Function".
 * It describes the two possibilities of getting into a step.
 * We can translate that into:
 * f(n)=f(n-1)+f(n-2)
 * Where f(n) is the number of ways getting into the nth step.
 * f(1)=1, f(2)=2, n>2
 */
public class ClimbingStairs {
    //Normally there are two ways coding a DP: loop or recursion
    public int climbStairs(int n) {
        //n is positive integer
        if(n==1) return 1;
        if(n==2) return 2;
        int i=3;
        int a=2, b=1, c=0;
        while(i<=n){
            c=a+b;
            b=a;
            a=c;
            i++;
        }
        System.out.println(c);
        return c;
    }

    public int climbStairsRecur(int n){
        //Termination Conditions
        if(n==1) return 1;
        if(n==2) return 2;

        return climbStairsRecur(n-1)+climbStairsRecur(n-2);
    }
}
