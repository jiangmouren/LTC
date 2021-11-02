package com.mycompany.app.dp;

/**
 * Given a string s, find the longest palindromic subsequence's length in s.
 * You may assume that the maximum length of s is 1000.

 * Example 1:
 * Input:
 * "bbbab"
 * Output:
 * 4
 * One possible longest palindromic subsequence is "bbbb".
 *
 * Example 2:
 * Input:
 * "cbbd"
 * Output:
 * 2
 * One possible longest palindromic subsequence is "bb".
 *
 */

/**
 * Analysis:
 * 首先注意这道题问的是subsequence,跟另外一道类似的substring题目是不一样的！！!
 *
 * Now we have 2 possibilities:
 * 1. left==right, so both left and right will counted.
 * 2. left!=right, in this case, either left, or right could potentially be counted, but not both at the same time.
 *
 * So our new recursion will be:
 * dp[i][j] = (s.charAt(i)==s.charAt(j)) ? dp[i+1][j-1]+2 : Max{dp[i][j-1], dp[i+1][j]};
 *
 * Base case: i==j, dp[i][i] == 1
 *
 * Topological order: smaller substrings to larger substrings;
 *                    for row: bottom-->up, for column: left-->right.
 *                    具体是一列一列construct还是一行一行construct都可以，只要遵照上面的顺序
 *
 * Prove when s.charAt(i)==s.charAt(j), dp[i+1][j-1]+2 is at least as good as Max{dp[i][j-1], dp[i+1][j]}?
 * Let's say s.charAt(i)==s.charAt(j)=='c'.
 * For dp[i][j-1], it's only different from dp[i+1][j-1], when s.charAt(i) is counted.
 * when it is not counted, dp[i+1][j-1] is the same as dp[i][j-1]. So dp[i+1][j-1]+2>dp[i][j-1].
 * When it is counted, dp[i][j-1] 中最长的palindromic subsequence will have following pattern: c+(some palindrom)+c
 * dp[i+1][j-1]当中最起码有如下满足条件的subsequence: (some palindrom)
 * 然后再加上2，最起码跟dp[i][j-1]结果是一样的。
 */

class LongestPalindromicSubsequence {
    public int longestPalindromeSubseq(String s) {
        int[][] dp = new int[s.length()][s.length()];
        for(int i=s.length()-1; i>=0; i--){
            for(int j=i; j<s.length(); j++){
                //base case
                if(i==j){
                    dp[i][j] = 1;
                }
                else{
                    dp[i][j] = (s.charAt(i)==s.charAt(j)) ? dp[i+1][j-1]+2 : Math.max(dp[i][j-1], dp[i+1][j]);
                }
            }
        }
        return dp[0][s.length()-1];
    }
}
