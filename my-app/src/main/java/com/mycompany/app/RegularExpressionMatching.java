package com.mycompany.app;

/**
 * https://leetcode.com/problems/regular-expression-matching/
 * Given an input string (s) and a pattern (p), implement regular expression matching with support for '.' and '*' where:
 *
 * '.' Matches any single character.​​​​
 * '*' Matches zero or more of the preceding element.
 * The matching should cover the entire input string (not partial).
 *
 * Example 1:
 * Input: s = "aa", p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 *
 * Example 2:
 * Input: s = "aa", p = "a*"
 * Output: true
 * Explanation: '*' means zero or more of the preceding element, 'a'. Therefore, by repeating 'a' once, it becomes "aa".
 *
 * Example 3:
 * Input: s = "ab", p = ".*"
 * Output: true
 * Explanation: ".*" means "zero or more (*) of any character (.)".
 *
 * Example 4:
 * Input: s = "aab", p = "c*a*b"
 * Output: true
 * Explanation: c can be repeated 0 times, a can be repeated 1 time. Therefore, it matches "aab".
 *
 * Example 5:
 * Input: s = "mississippi", p = "mis*is*p*."
 * Output: false
 *
 * Constraints:
 * 0 <= s.length <= 20
 * 0 <= p.length <= 30
 * s contains only lowercase English letters.
 * p contains only lowercase English letters, '.', and '*'.
 * It is guaranteed for each appearance of the character '*', there will be a previous valid character to match.
 */

public class RegularExpressionMatching {
    public boolean isMatch(String s, String p) {
        //注意这里都是length + 1
        boolean dp[][] = new boolean[s.length()+1][p.length()+1];
        //initial value,当s&p都visit结束的时候自然就true
        dp[s.length()][p.length()] = true;

        //分成两大类，p[j+1]=='*'的情况和p[j+1]!='*'的情况
        //dp[i][j] = p[j+1]=='*' ? dp[i][j+2] || (firstMatch && dp[i+1][j]) : firstMatch && dp[i+1][j+1];
        //p[j+1]=='*'的时候，可以选择match 0个preceding element，或者如果能match的话就match，但是j不变，因为要保留match多个的选项
        //当p[j+1]=='*'的时候，(p[j], p[j+1])总是当成一组处理
        //另外在toplogical order上，此题既可以一行一行从下往上写，也可以一列一列从右往左写
        //最后一行和最右一列的值，都是有“实际意义”的：
        //最后一行的时候i=s.length(),代表s已经match完了，这时候dp[s.length()][j]想要是true的可能只有一个就是p[j:p.length()]刚好都是一对一对的"a*"样式，然后一对都match 0.
        //最右一列的时候j=p.length(),代表p已经全用完了，这时候dp[i][p.length()]想要是true的情况只能是i=s.length()，所以，最后一列除了dp[s.length()][p.length()]是true，其余全是false
        for(int i=s.length(); i>=0; i--){
            for(int j=p.length()-1; j>=0; j--){
                boolean firstMatch = i<s.length() && (s.charAt(i)==p.charAt(j) || p.charAt(j)=='.');
                if(j+1<p.length() && p.charAt(j+1)=='*'){
                    dp[i][j] = dp[i][j+2] || firstMatch && dp[i+1][j];
                }
                else{
                    dp[i][j] = firstMatch && dp[i+1][j+1];//这里firstMatch帮助short circuit dp[i+1][j+1]可能出现的index out of bound
                }
            }
        }
        return dp[0][0];
    }
}