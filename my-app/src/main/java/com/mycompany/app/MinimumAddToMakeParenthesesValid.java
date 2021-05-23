package com.mycompany.app;

/**
 * https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
 * Given a string s of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')',
 * and in any positions ) so that the resulting parentheses string is valid.
 * Formally, a parentheses string is valid if and only if:
 * It is the empty string, or
 * It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * It can be written as (A), where A is a valid string.
 * Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string valid.
 *
 * Example 1:
 * Input: s = "())"
 * Output: 1
 *
 * Example 2:
 * Input: s = "((("
 * Output: 3
 *
 * Example 3:
 * Input: s = "()"
 * Output: 0
 *
 * Example 4:
 * Input: s = "()))(("
 * Output: 4
 *
 * Note:
 * s.length <= 1000
 * s only consists of '(' and ')' characters.
 */
public class MinimumAddToMakeParenthesesValid {
    //本质上add/remove is the same in the sense of making parentheses valid
    //所以跟其它几道remove to make valid的思路是一样的
    public int minAddToMakeValid(String s) {
        int cnt = 0;
        int res = 0;
        for(int i=0; i<s.length(); i++){
            if(s.charAt(i)=='('){
                cnt++;
            }
            else{
                cnt--;
            }
            if(cnt<0){
                res -= cnt;
                cnt = 0;
            }
        }
        res += cnt;
        return res;
    }
}
