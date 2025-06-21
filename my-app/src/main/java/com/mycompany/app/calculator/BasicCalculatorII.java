package com.mycompany.app.calculator;
import java.util.*;

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
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int ptr = 0;
        char op = '+';
        while(ptr<s.length()){
            if(s.charAt(ptr)==' '){
                ptr++;
                continue;
            }
            else if(Character.isDigit(s.charAt(ptr))){
                int temp = 0;
                while(ptr<s.length() && Character.isDigit(s.charAt(ptr))){
                    temp = 10 * temp + (int)(s.charAt(ptr)-'0');
                    ptr++;
                }
                if(op=='+'){
                    stack.push(temp);
                }
                else if(op=='-'){
                    stack.push(-temp);
                }
                else if(op=='*'){
                    int pre = stack.pop();
                    stack.push(pre * temp);
                }
                else{
                    int pre = stack.pop();
                    stack.push(pre / temp);
                }//这种情况ptr不用再++了
            }
            else{
                op = s.charAt(ptr);
                ptr++;
            }
        }
        int temp = 0;
        while(!stack.isEmpty()){
            temp += stack.pop();
        }
        return temp;
    }

    //这种解法思路上跟上面相同，可以理解为对上面我的解法的一种优化，用partial result的方式，不用两边loop就可以
    public int calculatesln2(String s) {
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
}
