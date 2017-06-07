package Finished;

/**
 * Created by jiangmouren on 6/4/17.
 */

/**
 * Question:
 * Given a non-negative integer num, repeatedly add all its digits until the result has only one digit.
 * For example:
 * Given num = 38, the process is like: 3 + 8 = 11, 1 + 1 = 2. Since 2 has only one digit, return it.
 * Follow up:
 * Could you do it without any loop/recursion in O(1) runtime?
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
    public int addDigits(int num) {
        int result = num % 9;
        if(result==0) return 9;
        else return result;
    }
}
