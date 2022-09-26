package com.mycompany.app.math;

/**
 * Question: https://leetcode.com/problems/add-digits/
 * Given an integer num, repeatedly add all its digits until the result has only one digit, and return it.
 *
 * Example 1:
 * Input: num = 38
 * Output: 2
 * Explanation: The process is
 * 38 --> 3 + 8 --> 11
 * 11 --> 1 + 1 --> 2
 * Since 2 has only one digit, return it.
 *
 * Example 2:
 * Input: num = 0
 * Output: 0
 *
 * Constraints:
 * 0 <= num <= 231 - 1
 *
 * Follow up: Could you do it without any loop/recursion in O(1) runtime?
 */

/**
 * Analysis:
 * Typical approach would be using while loop or recursion.
 * But if no "loop/recursion" and even O(1) time, it is obvious that some kind of math tricks or pattern must exists.
 * In this case, the final results can only be 1, 2, 3, ... , 9.
 * 也就是说不管从任何一个数出发，最终一定都收敛到上述9个Node上。
 * 那我们就从这9个node往回反着看，看能找到什么规律
 * 规律就是在迭代的过程中，整个链条上的数，对9求余数，都是一样的。
 * 那么给定任何一个数，就可以知道他会最终收敛在1-9那个数上了：看它对9求余数，跟1-9当中哪个对9求余数结果相同
 *
 * The next problem is mathematically how to prove this.
 * Because of the recursive nature of this problem, all we need to prove is that:
 * A=x+10y+100z and B=x+y+z will have the same remainder when divided by 9.
 *
 * A=x+y+z+[(10-1)y+(100-1)z]=B+[(10-1)y+(100-1)z]
 * Because [(10-1)y+(100-1)z] is dividable by 9, so A and B will share the same remainder when divided by 9.
 *
 */
public class AddDigits {
    //参考：https://en.wikipedia.org/wiki/Digital_root
    public int addDigits(int num) {
        if(num==0){
            return 0;
        }
        int res = num % 9;
        if(res==0){
            res = 9;
        }
        return res;
    }
}
