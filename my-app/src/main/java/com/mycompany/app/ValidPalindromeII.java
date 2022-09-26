package com.mycompany.app;

/**
 * https://leetcode.com/problems/valid-palindrome-ii/
 * Given a non-empty string s, you may delete at most one character. Judge whether you can make it a palindrome.
 *
 * Example 1:
 * Input: "aba"
 * Output: True
 * Example 2:
 * Input: "abca"
 * Output: True
 * Explanation: You could delete the character 'c'.
 * Note:
 * The string will only contain lowercase characters a-z. The maximum length of the string is 50000.
 */
//一个简单的Backtracking，注意两点：只能有一次；遇到不相等，两种方式都要试
//"lcupuupucul"这种情况，如果只试一种就有可能出错
public class ValidPalindromeII {
    public boolean validPalindrome(String s) {
        return helper(s, 0, s.length()-1, false);
    }

    private boolean helper(String s, int left, int right, boolean used){
        while(left<=right){
            if(s.charAt(left)==s.charAt(right)){
                left++;
                right--;
            }
            else{
                if(used){
                    return false;
                }
                if(s.charAt(left+1)==s.charAt(right) && helper(s, left+1, right, true)){
                    return true;
                }
                if(s.charAt(left)==s.charAt(right-1) && helper(s, left, right-1, true)){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return true;
    }
}
