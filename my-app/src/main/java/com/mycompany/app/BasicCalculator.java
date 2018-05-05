package com.mycompany.app;
import com.mycompany.app.BasicCalculatorGeneric;

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
 * Refer to BasicCalculatorGeneric for the generic type and solution of calculator problem.
 * For this specific problem, it is a near normalized version: we don't need to worry about the parenthesis,
 * but we still need to remove the spaces.
 *
 * One common mistake for this problem is that think the "()" does not really matter in this problem because we do not
 * have '*' / '/'. But actually it matters because 5-(3-2) is different from 5-3-2 !!!
 */
class BasicCalculator {
    BasicCalculatorGeneric obj = new BasicCalculatorGeneric();
    public int calculate(String s) {
        return obj.calculate(s);
    }
}
