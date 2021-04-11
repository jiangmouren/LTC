package com.mycompany.app;

/**
 * https://leetcode.com/problems/reverse-string/
 * Write a function that reverses a string. The input string is given as an array of characters char[].
 *
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * You may assume all the characters consist of printable ascii characters.
 *
 * Example 1:
 * Input: ["h","e","l","l","o"]
 * Output: ["o","l","l","e","h"]
 *
 * Example 2:
 * Input: ["H","a","n","n","a","h"]
 * Output: ["h","a","n","n","a","H"]
 */
public class ReverseString{
    public void reverseString(char[] s) {
        int ptr0 = 0;
        int ptr1 = s.length-1;
        while(ptr0<ptr1){
            char temp = s[ptr0];
            s[ptr0] = s[ptr1];
            s[ptr1] = temp;
            ptr0++;
            ptr1--;
        }
    }
}