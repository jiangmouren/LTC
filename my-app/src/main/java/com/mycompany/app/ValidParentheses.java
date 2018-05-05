package com.mycompany.app;
/**
 * Question:
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * The brackets must close in the correct order, "()" and "()[]{}" are all valid but "(]" and "([)]" are not.
 */

import java.util.*;

/**
 * Analysis:
 * Intuitively this should be a stack problem, the last came bracket need to be first checked.
 */

public class ValidParentheses {
    public boolean validParentheses(String str){
        if(str==null) throw new IllegalArgumentException("Input cannot be null");
        if(str.length()==0) return true;
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i)=='(' || str.charAt(i)=='[' || str.charAt(i)=='{'){
                stack.add(str.charAt(i));
            }
            else if(str.charAt(i)==')'){
                if(stack.peek()!='(') return false;
                else stack.pop();
            }
            else if(str.charAt(i)==']'){
                if(stack.peek()!='[') return false;
                else stack.pop();
            }
            else{
                if(stack.peek()!='{') return false;
                else stack.pop();
            }
        }
        return stack.isEmpty();
    }
}

