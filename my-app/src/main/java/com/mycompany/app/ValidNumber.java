package com.mycompany.app;

/**
 * https://leetcode.com/problems/valid-number/
 * A valid number can be split up into these components (in order):
 * 1. A decimal number or an integer.
 * 2. (Optional) An 'e' or 'E', followed by an integer.
 *
 * A decimal number can be split up into these components (in order):
 * 1. (Optional) A sign character (either '+' or '-').
 * 2. One of the following formats:
 *    1. At least one digit, followed by a dot '.'.
 *    2. At least one digit, followed by a dot '.', followed by at least one digit.
 *    3. A dot '.', followed by at least one digit.
 *
 * An integer can be split up into these components (in order):
 * 1. (Optional) A sign character (either '+' or '-').
 * 2. At least one digit.
 *
 * For example, all the following are valid numbers: ["2", "0089", "-0.1", "+3.14", "4.", "-.9", "2e10", "-90E3", "3e+7", "+6e-1", "53.5e93", "-123.456e789"], while the following are not valid numbers: ["abc", "1a", "1e", "e3", "99e2.5", "--6", "-+3", "95a54e53"].
 * Given a string s, return true if s is a valid number.
 *
 * Example 1:
 * Input: s = "0"
 * Output: true
 *
 * Example 2:
 * Input: s = "e"
 * Output: false
 *
 * Example 3:
 * Input: s = "."
 * Output: false
 *
 * Example 4:
 * Input: s = ".1"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 20
 * s consists of only English letters (both uppercase and lowercase), digits (0-9), plus '+', minus '-', or dot '.'.
 */

public class ValidNumber {
    public boolean isNumber(String s) {
        int n = s.length();
        int ptr = 0;
        while(ptr<n && s.charAt(ptr)!='e' && s.charAt(ptr)!='E'){
            ptr++;
        }
        if(ptr<n){
            return (isInteger(s, 0, ptr-1) || isDecimal(s, 0, ptr-1)) && isInteger(s, ptr+1, n-1);
        }
        return (isInteger(s, 0, n-1) || isDecimal(s, 0, n-1));
    }

    private boolean isInteger(String s, int left, int right){
        //edge cases
        if(left>right){
            return false;
        }
        int ptr = left;
        if(s.charAt(ptr)=='+' || s.charAt(ptr)=='-'){
            ptr++;
        }
        if(ptr>right){
            return false;
        }
        while(ptr<=right && Character.isDigit(s.charAt(ptr))){
            ptr++;
        }
        return ptr>right;
    }

    private boolean isDecimal(String s, int left, int right){
        //edge cases
        if(left>right){
            return false;
        }
        int ptr = left;
        if(s.charAt(ptr)=='+' || s.charAt(ptr)=='-'){
            ptr++;
        }
        if(ptr>right){
            return false;
        }
        if(s.charAt(ptr)=='.'){
            ptr++;
            if(ptr>right){
                return false;
            }
            while(ptr<=right && Character.isDigit(s.charAt(ptr))){
                ptr++;
            }
            return ptr>right;
        }
        else if(Character.isDigit(s.charAt(ptr))){
            while(ptr<=right && Character.isDigit(s.charAt(ptr))){
                ptr++;
            }
            if(ptr>right || s.charAt(ptr)!='.'){
                return false;
            }
            ptr++;
            while(ptr<=right && Character.isDigit(s.charAt(ptr))){
                ptr++;
            }
            return ptr>right;
        }
        else{
            return false;
        }
    }
}
