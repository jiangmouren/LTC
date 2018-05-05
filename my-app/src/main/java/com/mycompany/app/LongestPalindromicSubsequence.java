package com.mycompany.app;

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
 * First of all the following recursive function is not correct for this:
 * dp[i][j] = (s.charAt(i)==s.charAt(j)) ? dp[i+1][j-1]+2 : dp[i+1][j-1];
 * It will be correct, if the problem is asking from the longest substring instead of subsequence.
 * Because consider the following example "bcbbabc",
 * even if 'b'!='c', but either left side 'b' or right side 'c' can still contribute to the subsequence,
 * and each results in: "bbbb" and "cbbbc"/"cbabc"
 * Now what?
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
 *                    for i: large-->small, for j: small--large;
 *                    for row: bottom-->up, for column: left-->right.
 *
 * Prove when s.charAt(i)==s.charAt(j), dp[i+1][j-1]+2 is at least as good as Max{dp[i][j-1], dp[i+1][j]}?
 * Let's say s.charAt(i)==s.charAt(j)=='c'.
 * For dp[i][j-1], it's only different from dp[i+1][j-1], when 'c' is counted.
 * So the pattern will be like:
 * c+(some palindrom)+c+s.charAt(j)
 * when s.charAt(j)==c, you will at least have that same (some palindrom)
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
