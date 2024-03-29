package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/power-of-two/
 * Given an integer n, return true if it is a power of two. Otherwise, return false.
 * An integer n is a power of two, if there exists an integer x such that n == 2x.
 *
 * Example 1:
 * Input: n = 1
 * Output: true
 * Explanation: 20 = 1
 *
 * Example 2:
 * Input: n = 16
 * Output: true
 * Explanation: 24 = 16
 *
 * Example 3:
 * Input: n = 3
 * Output: false
 *
 * Example 4:
 * Input: n = 4
 * Output: true
 *
 * Example 5:
 * Input: n = 5
 * Output: false
 *
 * Constraints:
 * -231 <= n <= 231 - 1
 *
 * Follow up: Could you solve it without loops/recursion?
 */

/**
 * Analysis:
 * No way to do it in O(1), can do O(lgn)
 */
public class PowerOfTwo {
    //Bit manipulation 写法：
    public boolean isPowerOfTwo(int n) {
        //要领就是x&(-x)会只留下原来x当中最右侧的啊一个1,其余的bit全都set成0
        //因为Power of 2的数，原本就只有最右侧一个1，所以x&(-x)就刚好等于x
        if (n == 0) return false;//上述关系刚好对想x==0也成立，所以要排除
        long x = (long) n; //注意因为n的区间左右都达到了int的极限，所以做-x操作的时候会有益处出现
        return (x & (-x)) == x;
    }

    //常规数学写法: 要注意n可能是负数，以及n刚好降阶到1的时候
    public boolean isPowerOfTwoLoop1(int n) {
        if(n<1){
            return false;
        }
        while(n>=1){
            if(n!=1 && n%2!=0){
                return false;
            }
            n = n/2;
        }
        return true;
    }

    //下面的写法最简洁
    public boolean isPowerOfTwoLoop2(int n) {
        while(n>=1){
            if(n==1) return true;
            if(n%2!=0) return false;
            n=n/2;
        }
        return false;
    }

}
