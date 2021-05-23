package com.mycompany.app;
import java.util.Stack;

/**
 * Question:
 * https://leetcode.com/problems/basic-calculator/
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
 * One common mistake for this problem is that think the "()" does not really matter in this problem because we do not
 * have '*' / '/'. But actually it matters because 5-(3-2) is different from 5-3-2 !!!
 *
 * 思路就是从左到右把每个遇到的Operand accumulate起来，当中要“是+，还是-”的问题。
 * 这个问题主要由括号引进的。所以本质上只要figure out如何处理括号导致的加减变号的问题就可以了。
 * 然后就是要考虑到nested括号，用stack来buffer括号的总符号。
 */
class BasicCalculator {
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        int sign = 1;
        stack.push(1);

        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)==' '){
                continue;
            }
            else if(s.charAt(i)=='('){
                stack.push(stack.peek()*sign);//Buffer the outer sign for current parenthesis.
                sign = 1;//Reset sign once enters parenthesis.
            }
            else if(s.charAt(i)==')'){
                stack.pop();//drop the sign for just closed parenthesis.
            }
            else if(s.charAt(i)=='+'){
                sign = 1;
            }
            else if(s.charAt(i)=='-'){
                sign = -1;
            }
            else{
                int temp = s.charAt(i) - '0';
                while(i+1<s.length() && Character.isDigit(s.charAt(i+1))){//check i+1 before apply it to s
                    temp = temp*10 + s.charAt(++i) - '0';
                }
                res += sign*stack.peek()*temp;
            }
        }
        return res;
    }

    public int calculateRecur(String s) {
        long l1 = 0; //partial result for + & -
        int o1 = 1; // 1: +; -1: -
        long l2 = 0; //partial result

        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c==' '){
                continue;
            }
            else if(Character.isDigit(c)){
                l2 = c - '0';
                while(i+1<s.length() && Character.isDigit(s.charAt(i+1))){
                    l2 = l2*10 + s.charAt(++i)-'0';
                }
            }
            else if(c=='('){
                int leftCnt = 1;
                int k = i+1;
                while(k<s.length()){
                    if(s.charAt(k)=='('){
                        leftCnt++;
                    }
                    else if(s.charAt(k)==')'){
                        leftCnt--;
                    }
                    if(leftCnt==0){
                        break;
                    }
                    k++;
                }
                l2 = calculateRecur(s.substring(i+1, k));
                i = k; //i will increment in the for loop, no need to manually increment
            }
            else{ //(c=='+' || c=='-')
                l1 += o1*l2;
                o1 = (c=='+') ? 1 : -1;
                //reset segment
                l2 = 0;
            }
        }
        //handle the last segment
        return (int)(l1+o1*l2);
    }
}
