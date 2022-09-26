package com.mycompany.app;
/**
 * Question:
 * https://leetcode.com/problems/valid-parentheses/
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 * An input string is valid if:
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 *
 * Example 1:
 * Input: s = "()"
 * Output: true
 *
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 *
 * Example 3:
 * Input: s = "(]"
 * Output: false
 *
 * Example 4:
 * Input: s = "([)]"
 * Output: false
 *
 * Example 5:
 * Input: s = "{[]}"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 104
 * s consists of parentheses only '()[]{}'.
 */

import java.util.*;

/**
 * Analysis:
 * Intuitively this should be a stack problem, the last came bracket need to be first checked.
 */

public class ValidParentheses {
    public boolean isValid(String s) {
        //The easiest way for multi type parentheses is to use a stack.
        //if there's only one type of parentheses, just use running couters for left parentheses and right parentheses
        Stack<Character> stack = new Stack<>();
        for(int i = 0; i<s.length(); i++){
            char currChar = s.charAt(i);
            if(currChar == '(' || currChar == '[' || currChar == '{'){
                stack.add(currChar);
            }
            else if(stack.isEmpty()){
                return false;
            }
            else if(currChar == ')' && stack.pop() != '(' || currChar == ']' && stack.pop() != '[' || currChar == '}' && stack.pop() != '{'){
                return false;
            }
        }
        return stack.size()==0;
    }

    //注意不要犯下面错误，下面这种写法不能处理 "([)]"
    //想要改一下的话，我就必须加3个boolean type，来标记上一次的“开括号”是哪种类型
    /**
     *     public boolean isValid(String s) {
     *         int a = 0;
     *         int b = 0;
     *         int c = 0;
     *         for(int i=0; i<s.length(); i++){
     *             if(s.charAt(i)=='('){
     *                 a++;
     *             }
     *             else if(s.charAt(i)==')'){
     *                 a--;
     *                 if(a<0){
     *                     return false;
     *                 }
     *             }
     *             else if(s.charAt(i)=='['){
     *                 b++;
     *             }
     *             else if(s.charAt(i)==']'){
     *                 b--;
     *                 if(b<0){
     *                     return false;
     *                 }
     *             }
     *             else if(s.charAt(i)=='{'){
     *                 c++;
     *             }
     *             else{
     *                 c--;
     *                 if(c<0){
     *                     return false;
     *                 }
     *             }
     *         }
     *         return a==0 && b==0 && c==0;
     *     }
     */
}

