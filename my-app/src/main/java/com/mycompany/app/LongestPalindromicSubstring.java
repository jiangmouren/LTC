package com.mycompany.app;

/**
 * https://leetcode.com/problems/longest-palindromic-substring/
 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
 * Example:
 * Input: "babad"
 * Output: "bab"
 * Note: "aba" is also a valid answer.
 *
 * Example:
 * Input: "cbbd"
 * Output: "bb"
 */

/**
 * Analysis:
 * 最简单的思路就是挨个排查每一个可能的substring是不是palindromic，一共有n^2个substring，每个substring判断需要O(n)
 * 所以total time complexity: O(n^3)
 *
 * 更优的解法是用dp. 因为每个palindromic的string，两头各去掉字母，余下的还是palindromic string，所以这就构成一个
 * 可以使用dp的结构。因为有start & end两个变量，所以就是一个2D的dp.
 * dp[i][j] = dp[i+1][j-1] && s.charAt(i)==s.charAt(j)
 *
 * 还有一种O(n)的叫做Manacher’s Algorithm的解法。这种算法就是解决针对palindromic substring的。
 * https://leetcode.com/discuss/62835/o-n-time-manacher-algorithm
 * https://www.geeksforgeeks.org/manachers-algorithm-linear-time-longest-palindromic-substring-part-1/
 * TODO: 抽时间看下！
 * 下面的第三种解法。
 */

public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int left = 0;
        int right = 0;
        //initiate starting values
        for(int i=0; i<dp.length; i++){
            dp[i][i] = true;
            if(i-1>=0){
                dp[i][i-1] = true;
            }
        }
        for(int i=s.length()-2; i>=0; i--){
            for(int j=i+1; j<s.length(); j++){
                dp[i][j] = s.charAt(i)==s.charAt(j) && dp[i+1][j-1];
                if(dp[i][j] && j-i>right-left){
                    right = j;
                    left = i;
                }
            }
        }
        return s.substring(left, right+1);
    }

    //跟上面的主要区别是递归的实现。采用一条斜线，接着一条斜线的方式写。
    public String longestPalindromeImp2(String s) {
        boolean[][] dp = new boolean[s.length()][s.length()];
        int left = 0;
        int right = 0;
        //initiate starting values
        for(int i=0; i<dp.length; i++){
            dp[i][i] = true;
            if(i-1>=0){
                dp[i][i-1] = true;
            }
        }
        //k is the step not the index itself, 引入这个变量是为了，能够从左上往右下，斜着一排一排的递推
        //dp[i][j] = dp[i+1][j-1] && s.charAt(i)==s.charAt(j)
        for(int k=1; k<dp[0].length; k++){
            for(int i=0; i<dp.length; i++){
                int j=i+k;
                if(j<dp[0].length && dp[i+1][j-1] && s.charAt(i)==s.charAt(j)){
                    dp[i][j] = true;
                    if(j-i>right-left){
                        left = i;
                        right = j;
                    }
                }
            }
        }
        return s.substring(left, right+1);
    }


    //TODO: 抽时间看下！
    public String longestPalindromeManacher(String s) {
        int start=0, end=0;
        for(int i=0; i<s.length(); i++){
            int len1 = helper(s, i, i);
            int len2 = helper(s, i, i+1);
            int len = Math.max(len1, len2);
            if(len>end-start+1){
                start = i-(len-1)/2;
                end = i+len/2;
            }
        }
        return s.substring(start, end+1);
    }

    private int helper(String s, int l, int r){
        while(l>=0 && r<s.length() && s.charAt(l)==s.charAt(r)){
            l--;
            r++;
        }
        return r-l-1;//because l & r will both be out of bound
    }
}
