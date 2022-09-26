package com.mycompany.app.math;

/**
 * https://leetcode.com/problems/count-primes/
 * Count the number of prime numbers less than a non-negative number, n.
 *
 * Example 1:
 * Input: n = 10
 * Output: 4
 * Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.
 *
 * Example 2:
 * Input: n = 0
 * Output: 0
 *
 * Example 3:
 * Input: n = 1
 * Output: 0
 *
 * Constraints:
 * 0 <= n <= 5 * 106
 */

/**
 * Analysis:
 * https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes#Algorithm_complexity
 */
public class CountPrimes {
    public int countPrimes(int n) {
        //从2开始，mark off non-prime numbers, stop at sqrt(n)
        //以此把prime的整倍数都划掉，但是对每个prime p来说都从 p*p开始划掉
        //因为p * (p-1)等之前更小的已经在处理之前跟小的prime的数之前划掉过了
        //因此最大的需要考虑的prime在这里就是p*p<n (题目要求只考虑严格小于n的prime)
        boolean[] nonPrime = new boolean[n];
        for(int i=2; i*i<n; i++){
            if(!nonPrime[i]){
                for(int j=i*i; j<n; j+=i){
                    nonPrime[j] = true;
                }
            }
        }
        int cnt = 0;
        for(int i=2; i<n; i++){
            if(!nonPrime[i]){
                cnt++;
            }
        }
        return cnt;
    }
}
