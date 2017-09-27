package com.mycompany.app.sep22;

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

/**
 * Analysis:
 * This one is more generic, you will need to do recursion.
 */
public class BasicCalculatorII {
    //public int calculate(String s) {

    //}
    public int calculate(String s){
        int[] start = new int[1];
        start[0]=0;
        return (int)helper(s, start);
    }

    private long helper(String s, int[] start){
        int[] i = start;
        long res = 0;
        char op = '+';
        while(i[0]<s.length()){
            if(s.charAt(i[0])==')'){//Assuming if you see ')', you must be in a call stack and it's time to return.
                i[0]++;
                return res;
            }
            else if(s.charAt(i[0])=='('){
                i[0]++;
                res = helper2(res, op, helper(s, i));
                //TODO: how to increment i?!!!
            }
            else if(s.charAt(i[0])==' '){
                i[0]++;
                continue;
            }
            else if(s.charAt(i[0])=='+'){
                i[0]++;
                op = '+';
            }
            else if(s.charAt(i[0])=='-'){
                i[0]++;
                op = '-';
            }
            else if(s.charAt(i[0])=='*'){
                i[0]++;
                op = '*';
            }
            else if(s.charAt(i[0])=='/'){
                i[0]++;
                op = '/';
            }
            else{
                long temp = s.charAt(i[0])-'0';
                while(++i[0]<s.length() && Character.isDigit(s.charAt(i[0]))){//use ++i to increment the ptr
                    temp = temp *10 + s.charAt(i[0]) - '0';
                }
                //System.out.println("temp: "+temp);
                res = helper2(res, op, temp);
            }
        }
        return res;
    }

    private long helper2(long num1, char op, long num2){
        if(op=='+') return num1+num2;
        else if(op=='-') return num1-num2;
        else if(op=='*') return num1*num2;
        else{
            if(num2==0) throw new IllegalArgumentException("divide by 0");
            else return num1/num2;
        }
    }
}
