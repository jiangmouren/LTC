package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Question:
 * Implement a basic calculator to evaluate a simple expression string.
 * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces.
 * The integer division should truncate toward zero.
 * You may assume that the given expression is always valid.
 * Some examples:
 * "3+2*2" = 7
 * " 3/2 " = 1
 * " 3+5 / 2 " = 5
 * Note: Do not use the eval built-in library function.
 */


public class BasicCalculatorII {
    //要注意计算过程中出现Overflow的问题
    public int calculate(String s) {
        long l1 = 0; //partial result for + & -
        int o1 = 1; // 1: +; -1: -
        long l2 = 1; //partial result for * & /
        int o2 = 1; // 1: *; -1: /

        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c==' '){
                continue;
            }
            else if(Character.isDigit(c)){
                int temp = c - '0';
                while(i+1<s.length() && Character.isDigit(s.charAt(i+1))){
                    temp = temp*10 + s.charAt(++i)-'0';
                }
                l2 = (o2==1) ? l2*temp : l2/temp;
            }
            else if(c=='*'){
                o2 = 1;
            }
            else if(c=='/'){
                o2 = -1;
            }
            else if(c=='+'){
                l1 += o1*l2;
                o1 = 1;
                //reset segment
                l2 = 1;
                o2 = 1;
            }
            else{
                l1 += o1*l2;
                o1 = -1;
                //reset segment
                o2 = 1;
                l2 = 1;
            }
        }
        //handle the last segment
        return (int)(l1+o1*l2);
    }

    /**
     * Analysis:
     * 这道题我的解法属于典型的"divide and conquer"的思路。
     * 这道题目相较于BasicCalculator多了乘除法，那就把整个expression根据加减法，先分段。
     * 分段之后的每个expression之内就没有运算优先级的问题了。
     * 那么top level就是一个加减法的运算，每个分段全是乘除法的运算，而且也可以类似加减法一样解决。
     * Time: O(n); Space: O(n), because of the plusMinusPositions.
     */
    public int calculateSln2(String s) {
        List<Integer> plusMinusPositions = parse(s);
        int ptr = 0;
        int sign = 1;//这个top level的加减法没有括号，所以不需要用stack去buffer 括号外层的sign
        long res = 0;
        for(int position : plusMinusPositions){
            //evaluate sub-expression
            res += sign * evaluateMulDiv(s, ptr, position-1);
            //update sign for next sub-expression
            sign = s.charAt(position)=='-' ? -1 : 1;
            ptr = position+1;
        }
        //handle remaining expressions
        res += sign * evaluateMulDiv(s, ptr, s.length()-1);
        return (int)res;
    }

    //find positions of + & -
    private List<Integer> parse(String s){
        List<Integer> positions = new ArrayList<>();
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)=='+' || s.charAt(i)=='-'){
                positions.add(i);
            }
        }
        return positions;
    }

    //evaluate expressions with only multiplication and division
    //pay attention to empty spaces
    private long evaluateMulDiv(String s, int start, int end){
        long res = 1l;
        boolean empty = true;
        boolean mul = true;
        for(int i=start; i<=end; i++){
            if(s.charAt(i)==' '){
                continue;
            }
            else if(Character.isDigit(s.charAt(i))){//for a valid expression, guaranteed operand come before operators
                empty = false;
                int temp = s.charAt(i) - '0';
                while(i+1<=end && Character.isDigit(s.charAt(i+1))){
                    temp = temp*10 + s.charAt(++i) - '0';
                }
                res = mul ? res * temp : res / temp;
            }
            else if(s.charAt(i)=='*'){// mul
                mul = true;
            }
            else{// div
                mul = false;
            }
        }
        //this handles the single operand case
        return empty ? 0 : res;
    }
}
