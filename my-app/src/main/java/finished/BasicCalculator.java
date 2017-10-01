package finished;

/**
 * Question:
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string may contain open ( and closing parentheses ),
 * the plus + or minus sign -, non-negative integers and empty spaces .
 * You may assume that the given expression is always valid.
 * Some examples:
 * "1 + 1" = 2
 * " 2-1 + 2 " = 3
 * "(1+(4+5+2)-3)+(6+8)" = 23
 * Note: Do not use the eval built-in library function.
 */

/**
 * Analysis:
 * The normal way to solve this kind of Calculator problem is to use "recursion".
 * Because the problem solving process is essentially a "recursive process".
 * You recursively evaluate expression values, and with all the sub-expression values, original one is solved.
 *
 * But for this specific problem, we can do it easier because we only have "+" and "-".
 * The order does not really matters, we can just sum from left to right.
 * Just be careful with the ptr manipulation.
 * TODO:
 */
class BasicCalculator {
    //public int calculate(String s) {
    //}

}
