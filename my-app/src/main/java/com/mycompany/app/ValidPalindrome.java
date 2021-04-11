package com.mycompany.app;

/**
 * https://leetcode.com/problems/valid-palindrome/
 * Given a string s, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 *
 * Example 1:
 * Input: s = "A man, a plan, a canal: Panama"
 * Output: true
 * Explanation: "amanaplanacanalpanama" is a palindrome.
 *
 * Example 2:
 * Input: s = "race a car"
 * Output: false
 * Explanation: "raceacar" is not a palindrome.
 *
 *
 * Constraints:
 * 1 <= s.length <= 2 * 105
 * s consists only of printable ASCII characters.
 */
public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        int ptr0 = 0;
        int ptr1 = s.length()-1;
        while(ptr0<ptr1){
            if(!Character.isLetterOrDigit(s.charAt(ptr0))){
                ptr0++;
                continue;
            }
            if(!Character.isLetterOrDigit(s.charAt(ptr1))){
                ptr1--;
                continue;
            }
            if(Character.toLowerCase(s.charAt(ptr0))!=Character.toLowerCase(s.charAt(ptr1))){
                return false;
            }
            ptr0++;
            ptr1--;
        }
        return true;
    }
}
