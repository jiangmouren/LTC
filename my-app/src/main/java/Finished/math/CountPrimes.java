package Finished.math;

/**
 * Question:
 * Description:
 * Count the number of prime numbers less than a non-negative number, n.
 */

/**
 * Analysis:
 * The naive way to do this is to loop through the list and
 * for every number verify if it is a prime or not. O(n^2) complexity.
 * The typical improvement(actually just memorize it),
 * is to look ahead and chop off numbers that contains the current prime.
 * Similar to DP concept, in this way, O(1) complexity to determine if a given
 * number is a prime. This will reduce the complexity from O(n^2) to O(nk),
 * where k is the number of primes smaller than n.
 * The benefit comes from the fact that k, when n is big, is much smaller than n,
 * otherwise we not benefiting much.
 */
public class CountPrimes {
    public int countPrimes(int n){
        if(n<2) return 0;
        boolean[] flag = new boolean[n];
        int count = 0;
        int prime = 2;
        while(prime<=n){
            count++;
            chop(flag, prime);
            prime = getPrime(flag, prime);
        }
        return count;
    }

    private void chop(boolean[] flag, int prime){
        int coeff = prime;
        int ptr = prime;

        while(ptr<=flag.length){
            flag[ptr-1] = true;
            ptr = coeff * prime;
            coeff++;
        }
    }

    private int getPrime(boolean[] flag, int prime){
        //Whenever loop a array using a while(), pay attention to boundaries.
        while(prime<=flag.length && flag[prime-1]){
            prime++;
        }
        return prime;
    }

}
