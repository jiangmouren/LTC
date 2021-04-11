package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/strobogrammatic-number/
 * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
 * Write a function to determine if a number is strobogrammatic.
 * The number is represented as a string.
 * For example, the numbers "69", "88", and "818" are all strobogrammatic.
 */

public class StrobogrammaticNumber {
    public boolean isStrobogrammatic(String num) {
        int ptr0= 0;
        int ptr1 = num.length()-1;
        while(ptr0<=ptr1){
            char c1 = num.charAt(ptr0);
            char c2 = num.charAt(ptr1);
            if(!pair(c1, c2)){
                return false;
            }
            ptr0++;
            ptr1--;
        }
        return true;
    }

    private boolean pair(char c1, char c2){
        if(c1=='6'&&c2=='9' || c1=='9'&&c2=='6' || c1=='0'&&c2=='0' || c1=='1'&&c2=='1' || c1=='8'&&c2=='8'){
            return true;
        }
        return false;
    }
}
