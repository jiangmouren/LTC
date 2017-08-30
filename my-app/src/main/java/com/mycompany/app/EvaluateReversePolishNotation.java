package com.mycompany.app;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.

Valid operators are +, -, *, /. Each operand may be an integer or another expression.

Some examples:
  ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
  ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 TODO:
 */

/**
 * Analysis:
 * The way the data and operands stored makes it a perfect recursive case.
 */

public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        int[] ptr = {tokens.length-1};
        return helper(tokens, ptr);
    }

    //Assume it's valid input
    private int helper(String[] tokens, int[] ptr){
        if(tokens[ptr[0]].length()>1){
            return Integer.valueOf(tokens[ptr[0]]);//Need to know this Integer method
        }
        else if(tokens[ptr[0]]=="+"){
            ptr[0] -= 1;
            int right = helper(tokens, ptr);
            int left = helper(tokens, ptr);
            return left + right;
        }
        else if(tokens[ptr[0]]=="*"){
            ptr[0] -= 1;
            int right = helper(tokens, ptr);
            int left = helper(tokens, ptr);
            return left * right;
        }
        else if(tokens[ptr[0]]=="/"){
            ptr[0] -= 1;
            int right = helper(tokens, ptr);
            int left = helper(tokens, ptr);
            return left / right;
        }
        else{
            ptr[0] -= 1;
            int right = helper(tokens, ptr);
            int left = helper(tokens, ptr);
            return left - right;
        }
    }
}
