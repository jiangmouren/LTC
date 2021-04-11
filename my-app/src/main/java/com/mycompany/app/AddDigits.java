package com.mycompany.app;

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
 * All integers will converge into these 9 single digits and overall all integers will connected into a graph.
 * The questions is at any given vertex, there is a way I can tell immediately which leaf node I will converge to.
 * Which implies all vertices that converge to the same leave node, share some common properties.
 * And from those properties we can tell which leave you will converge to.
 * By looking at examples and intuitively all integers will converge into 1-9, you will think about "remainder" of 9.
 * Why not reminder of 10? Because that would mean 10 possibilities.
 *
 * That's actually true.
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
