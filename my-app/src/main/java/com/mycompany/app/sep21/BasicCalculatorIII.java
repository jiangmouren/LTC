package com.mycompany.app.sep21;

/**
 * Question:
 * This scope for this one is to write a generic solution for this Calculator thing.
 * When I sai "generic", I mean it can contain "()", "+", "-", "*" and "/".
 * And it can be arbitrarily nested.
 *
 * The approach I followed is:
 * 1. Add parenthesis(Generalization)
 * 2. Do recursion(Calculation)
 *
 * How to add parenthesis?
 * The idea is you loop though all the operands from left to right and every time you decide
 * if you need to add "(" / ")" / Nothing.
 * Rules:
 * 1. empty side == "+" == "-", has the same lowest priority;
 * 2. "*" == "/", has higher priority;
 * 3. if(priority_left<priority_right){add "(" on left side}; else if(priority_left>priority_right){add ")" on right side}
 * eg.
 * 2*3-2 --> (2*3-2 --> (2*3)-2
 * 2+3*2 --> 2+(3*2 --> 2+(3*2)
 *
 * How to recursively solve it?
 * After the generalization phase, you no longer need to worry about priorities.
 * All you need is just to to calculate from left to right.
 * You can use the suffix as the subproblem.
 * You solve a prefix, and combine with the remaining suffix problem, you are done.
 * Just like the case in "BasicCalculator", there you also do not have association issues.
 *
 * With the same approach I can write code to convert normal expression strings into reverse polished format.
 */
public class BasicCalculatorIII {
}
