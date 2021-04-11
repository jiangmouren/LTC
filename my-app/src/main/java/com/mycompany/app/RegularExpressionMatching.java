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
    //这里dp[i][j]所表达的意义：从s的position 'i'和p的position ‘j’开始，往后的的部分能否match
    //所以dp[0][0]就表示，从s&p的0位置开始，往右都能match
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


    /**
     * 以下是一种错误解法，却比较有利于帮助理解上面的解法。下面错误的原因是采取了一种greedy的思路，而非dp的思路
     * 既尝试用".*"/"a*"去match尽可能多的情况，而不是取explore每一种可能的情况。
     * 这就导致像：s="aaa", p="a*a"的情况会fail.
     * 上面解的dp的Index跟下面ptr0, ptr1相对应。
     */
    //"a*"可以跟""empty string match, 核心就是"a*"要当成一组来处理
    public boolean isMatchWrong(String s, String p) {
        int ptr0 = 0;//for s
        int ptr1 = 0;//for p

        while(ptr1<p.length()){
            if(ptr1+1<p.length() && p.charAt(ptr1+1)=='*'){
                if(ptr0>=s.length()){
                    ptr1++;
                    ptr1++;
                }
                else{
                    if(p.charAt(ptr1)=='.'){//".*" case
                        ptr0++;
                    }
                    else{//"c*" case
                        if(s.charAt(ptr0)==p.charAt(ptr1)){
                            ptr0++;
                        }
                        else{
                            ptr1++;
                            ptr1++;
                        }
                    }
                }
            }
            else{
                if(ptr0>=s.length()){
                    return false;
                }
                else{
                    if(Character.isAlphabetic(p.charAt(ptr1)) && s.charAt(ptr0)!=p.charAt(ptr1)){
                        return false;
                    }
                    else{
                        ptr0++;
                        ptr1++;
                    }
                }
            }
        }

        return ptr0>=s.length();
    }
}