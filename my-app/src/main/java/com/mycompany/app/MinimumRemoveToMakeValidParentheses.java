package com.mycompany.app;

/**
 * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
 * Given a string s of '(' , ')' and lowercase English characters.
 * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting parentheses string is valid and return any valid string.
 * Formally, a parentheses string is valid if and only if:
 * It is the empty string, contains only lowercase characters, or
 * It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * It can be written as (A), where A is a valid string.
 *
 * Example 1:
 * Input: s = "lee(t(c)o)de)"
 * Output: "lee(t(c)o)de"
 * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
 *
 * Example 2:
 * Input: s = "a)b(c)d"
 * Output: "ab(c)d"
 *
 * Example 3:
 * Input: s = "))(("
 * Output: ""
 * Explanation: An empty string is also valid.
 *
 * Example 4:
 * Input: s = "(a(b(c)d)"
 * Output: "a(b(c)d)"
 *
 * Constraints:
 * 1 <= s.length <= 10^5
 * s[i] is one of  '(' , ')' and lowercase English letters.
 */

//就是解决parentheses问题的老办法cnt，当中出现负值，直接跳过，最后出现正值，反向查找最近的几个'('丢掉
public class MinimumRemoveToMakeValidParentheses {
    public String minRemoveToMakeValid(String s) {
        StringBuilder buf = new StringBuilder();
        int cnt = 0;
        for(int i=0; i<s.length(); i++){
            if(Character.isAlphabetic(s.charAt(i))){
                buf.append(s.charAt(i));
            }
            else if(s.charAt(i)=='('){
                cnt++;
                buf.append(s.charAt(i));
            }
            else{
                cnt--;
                if(cnt>=0){
                    buf.append(s.charAt(i));
                }
                else{
                    cnt++;
                }
            }
        }
        if(cnt>0){
            for(int i=buf.length()-1; i>=0 && cnt>0; i--){
                if(buf.charAt(i)=='('){
                    buf.deleteCharAt(i);
                    cnt--;
                }
            }
        }
        return buf.toString();
    }
}
