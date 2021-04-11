package com.mycompany.app;

/**
 * https://leetcode.com/problems/palindromic-substrings/
 * Given a string, your task is to count how many palindromic substrings in this string.
 * The substrings with different start indexes or end indexes are counted as different substrings even they consist of same characters.
 *
 * Example 1:
 * Input: "abc"
 * Output: 3
 * Explanation: Three palindromic strings: "a", "b", "c".
 *
 * Example 2:
 * Input: "aaa"
 * Output: 6
 * Explanation: Six palindromic strings: "a", "a", "a", "aa", "aa", "aaa".
 *
 * Note:
 * The input string length won't exceed 1000.
 */

public class PalindromicSubstrings {
    public int countSubstrings(String s) {
        //dp[i][j] = (s[i]==s[j]) && dp[i+1][j-1], j>=i
        //Initial values: dp[i][j] = true, if i<=j
        //根据topo order，从左到右，一列一列的写，每一列，从上到下

        //Set up initial values
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        for(int i=0; i<n; i++){
            dp[i][i] = true;//i==j,主对角线初值
            if(i<n-1){//主对角线左侧斜着对角线初值
                dp[i+1][i] = true;
            }
        }

        int cnt = n;//主对角线上的数目为初值，既所有单个的char
        for(int j=1; j<n; j++){
            for(int i=0; i<j; i++){
                dp[i][j] = (s.charAt(i)==s.charAt(j)) && dp[i+1][j-1];
                if(dp[i][j]){
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
