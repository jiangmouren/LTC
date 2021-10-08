package com.mycompany.app.math;
/**
 * Question: https://leetcode.com/problems/factorial-trailing-zeroes/
 * Given an integer n, return the number of trailing zeroes in n!.
 * Follow up: Could you write a solution that works in logarithmic time complexity?
 *
 * Example 1:
 * Input: n = 3
 * Output: 0
 * Explanation: 3! = 6, no trailing zero.
 *
 * Example 2:
 * Input: n = 5
 * Output: 1
 * Explanation: 5! = 120, one trailing zero.
 *
 * Example 3:
 * Input: n = 0
 * Output: 0
 *
 * Constraints:
 * 0 <= n <= 104
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
    //这个题的本质是要确定在n!的展开式中能拆出多少个5
    //每一个trailing zero的出现，都源自于一对5*2的组合，而2出现的频次显然是高于5的
    //所以问题的关键就落在了能拆出多少个5上
    //每一个是5的倍数的数，必然可以拆出1个5；每一个是25的倍数的数，必然还能比满足前面条件的数再多拆出1个5；每个是125的倍数的数，必然还能比满足前面条件的数再多拆出1个5
    public int trailingZeroes(int n) {
        int divisor = 5;
        int res = 0;
        while(divisor<=n){
            res += n/divisor;
            divisor *= 5;
        }
        return res;
    }
}
