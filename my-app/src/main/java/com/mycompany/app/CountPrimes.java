package com.mycompany.app;

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
        boolean[] isPrime = new boolean[n];
        //注意一定要从2开始
        for(int i=2; i<n; i++){
            isPrime[i] = true;
        }

        //从2开始，mark off non-prime numbers, stop at sqrt(n)
        for(int i=2; i*i<n; i++){
            if(!isPrime[i]){
                continue;
            }
            for(int j=i*i; j<n; j+=i){
                isPrime[j] = false;
            }
        }
        int cnt = 0;
        for(int i=0; i<n; i++){
            if(isPrime[i]){
                cnt++;
            }
        }
        return cnt;
    }
}
