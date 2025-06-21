package com.mycompany.app.dp;

/**
 * Question:
 * Given two words word1 and word2, find the minimum number of steps required to convert word1 to word2.
 * (each operation is counted as 1 step.)
 * You have the following 3 operations permitted on a word:
 * a) Insert a character
 * b) Delete a character
 * c) Replace a character
 * https://leetcode.com/submissions/detail/49957701/
 */

/**
 * Analysis:
 * 1. 题目里说有三种操作，其实我们只用考虑两种操作，因为insert跟delete是互反的操作，我们只考虑delete
 * 2. 我们考虑dp[i][j]表达word1[0:i+1]跟word2[0:j+1]之间的edit distance
 * 我们的递推关系可以从下面分析得出:
 * 1. if word1.charAt(i) == word2.charAt(j), i-- && j-- and we will be looking at word1[0: i--], word2[0: j--]
 * 2. else we can do one of the following 3:
 * -> delete word1.charAt(i): we only need to look at word1[0: i--], word2[0: j]
 * -> replace word1.charAt(i): we only need to look at word1[i--], word2[j--]
 * -> delete word2.charAt(j): we only need to look at word1[0: i], word2[0: j--]
 *
 * Base cases:
 * what is the edit distance when i==0? --> it is j!
 * what is the edit distance when j==0? --> it is i!
 *
 * Topological Order:
 * row-wise(j), left to right;
 * column-wise(i), up to down;
 * Between row-column, it is symmetric, we can do either row first or column first.
 *
 * So here is what will end up with:
 * dp[i][j] = (word1.charAt(i-1)==word2.charAt(j-1)) ? dp[i-1][j-1] : 1 + min{dp[i-1][j-1], dp[i-1][j], dp[i][j-1]};
 * i: 1->word1.length; j: 1->word2.length
 * dp[0][j] = j; dp[i][0] = i;
 *
 * The original problem is dp[word1.length][word2.length]
 */

class EditDistance {
    public int minDistance(String word1, String word2) {
        if(word1==null || word2==null) {
            throw new IllegalArgumentException("Inputs cannot be null");
        }
        int l1 = word1.length();
        int l2 = word2.length();
        int[][] dp = new int[l1+1][l2+1];
        for(int i=0; i<=l1; i++){
            dp[i][0] = i;
        }
        for(int j=0; j<=l2; j++){
            dp[0][j] = j;
        }
        for(int i=1; i<=l1; i++){//注意从1开始
            for(int j=1; j<=l2; j++){
                dp[i][j] = (word1.charAt(i-1)==word2.charAt(j-1)) ? dp[i-1][j-1]
                                : (1 + Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]));
            }
        }
        return dp[l1][l2];
    }
}

