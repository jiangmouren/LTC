package com.mycompany.app;

/**
 * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
 * For example,
 * "A man, a plan, a canal: Panama" is a palindrome.
 * "race a car" is not a palindrome.
 * Note:
 * Have you consider that the string might be empty? This is a good question to ask during an interview.
 * For the purpose of this problem, we define empty string as valid palindrome.
 */


/**
 * Analysis:
 * The only catch is all the edge cases, Character.isAlphabetic and out of bound cases
 */

public class ValidPalindrome {
    public boolean validPalindrome(String str){
        if(str==null) throw new IllegalArgumentException("Input cannot be null");
        if(str.length()==0) return true;
        int ptr1 = 0, ptr2 = str.length()-1;
        while(ptr1<=ptr2){
            //whenever using pointers, especially in nested loops, pay special attention to bound conditions.
            while(ptr1<=ptr2 && !Character.isAlphabetic(str.charAt(ptr1))){
                ptr1++;
            }
            while(ptr1<=ptr2 && !Character.isAlphabetic(str.charAt(ptr2))){
                ptr2--;
            }
            if(ptr1<=ptr2 && Character.toLowerCase(str.charAt(ptr1))!=Character.toLowerCase(str.charAt(ptr2))) return false;
            else{
                ptr1++;
                ptr2--;
            }
        }
        return true;
    }
}
