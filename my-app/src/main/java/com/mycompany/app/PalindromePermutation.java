package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/palindrome-permutation/
 * Given a string s, return true if a permutation of the string could form a palindrome.
 *
 * Example 1:
 * Input: s = "code"
 * Output: false
 *
 * Example 2:
 * Input: s = "aab"
 * Output: true
 *
 * Example 3:
 * Input: s = "carerac"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 5000
 * s consists of only lowercase English letters.
 */

public class PalindromePermutation {
    public boolean canPermutePalindrome(String s) {
        //本质上是要判断只能有一个奇数的字母
        int[] cnt = new int[26];
        for(int i=0; i<s.length(); i++){
            cnt[s.charAt(i)-'a']++;
        }
        int odd = 0;
        for(int num : cnt){
            if(num%2==1){
                odd++;
                if(odd>1){
                    return false;
                }
            }
        }
        return true;
    }
}
