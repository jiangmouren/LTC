package com.mycompany.app.linearStructure;

/**
 * https://leetcode.com/problems/add-strings/
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
 *
 * Note:
 * The length of both num1 and num2 is < 5100.
 * Both num1 and num2 contains only digits 0-9.
 * Both num1 and num2 does not contain any leading zero.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */

public class AddStrings {
    public String addStrings(String num1, String num2) {
        StringBuilder buf = new StringBuilder();
        int c = 0;
        int ptr1 = num1.length()-1;
        int ptr2 = num2.length()-1;

        while(ptr1>=0 || ptr2>=0){
            if(ptr1>=0){
                c += (num1.charAt(ptr1)-'0');
                ptr1--;
            }
            if(ptr2>=0){
                c += (num2.charAt(ptr2)-'0');
                ptr2--;
            }
            if(c>9){
                buf.append(c-10);
                c = 1;
            }
            else{
                buf.append(c);
                c = 0;
            }
        }
        if(c>0){
            buf.append(c);
        }
        buf.reverse();
        return buf.toString();
    }
}
