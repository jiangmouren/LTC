package com.mycompany.app;
/**
 * Question:
 * Given an integer n, return the number of trailing zeroes in n!.
 * Note: Your solution should be in logarithmic time complexity.
 */

/**
 * Analysis:
 * A very good Math problem.
 * 1. the number of trailing zeroes depends on the number of (2, 5) factor pairs
 * 2. there are more 2 factors than 5 factors. 
 * 3. So the number of trailing zeros depends on the number of factor 5;
 *
 * n/5 we have the number of factor 5^1;
 * n/25 we have the number of factor 5^2, which should be count twice;
 * n/125 we have the number of factor 5^3, which should be count again one more time.
 */
public class FactorialTrailingZeroes{
    public int solution(int n){
        int factor = 5;
        int cnt = 0;
        while(factor <= n){
            int inc = n/factor;
            cnt+=inc;
            factor*=5;
        }
        return cnt;
    }

}
