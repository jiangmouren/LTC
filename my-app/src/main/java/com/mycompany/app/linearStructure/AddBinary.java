package com.mycompany.app.linearStructure;
/**
 * https://leetcode.com/problems/add-binary/
 * Given two binary strings a and b, return their sum as a binary string.
 *
 * Example 1:
 * Input: a = "11", b = "1"
 * Output: "100"
 *
 * Example 2:
 * Input: a = "1010", b = "1011"
 * Output: "10101"
 *
 * Constraints:
 * 1 <= a.length, b.length <= 104
 * a and b consist only of '0' or '1' characters.
 * Each string does not contain leading zeros except for the zero itself.
 */

public class AddBinary {
    public String addBinary(String a, String b) {
        StringBuilder buf = new StringBuilder();
        int ptr0 = a.length()-1;
        int ptr1 = b.length()-1;
        int c = 0;
        while(ptr0>=0 || ptr1>=0){
            if(ptr0>=0){
                c += (a.charAt(ptr0) - '0');
                ptr0--;
            }
            if(ptr1>=0){
                c += (b.charAt(ptr1) - '0');
                ptr1--;
            }
            if(c==0){
                buf.append(c);
            }
            else if(c==1){
                buf.append(1);
                c = 0;
            }
            else if(c==2){
                buf.append(0);
                c = 1;
            }
            else{
                buf.append(1);
                c = 1;
            }
        }
        if(c==1){
            buf.append(1);
        }
        return buf.reverse().toString();
    }
}
