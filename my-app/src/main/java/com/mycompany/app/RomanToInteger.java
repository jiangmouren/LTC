package com.mycompany.app;
import java.util.*;
/**
 * https://leetcode.com/problems/roman-to-integer/
 * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
 *
 * Symbol       Value
 * I             1
 * V             5
 * X             10
 * L             50
 * C             100
 * D             500
 * M             1000
 * For example, 2 is written as II in Roman numeral, just two one's added together.
 * 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.
 *
 * Roman numerals are usually written largest to smallest from left to right.
 * However, the numeral for four is not IIII. Instead, the number four is written as IV.
 * Because the one is before the five we subtract it making four.
 * The same principle applies to the number nine, which is written as IX.
 * There are six instances where subtraction is used:
 *
 * I can be placed before V (5) and X (10) to make 4 and 9.
 * X can be placed before L (50) and C (100) to make 40 and 90.
 * C can be placed before D (500) and M (1000) to make 400 and 900.
 * Given a roman numeral, convert it to an integer.
 *
 * Example 1:
 * Input: s = "III"
 * Output: 3
 *
 * Example 2:
 * Input: s = "IV"
 * Output: 4
 *
 * Example 3:
 * Input: s = "IX"
 * Output: 9
 *
 * Example 4:
 * Input: s = "LVIII"
 * Output: 58
 * Explanation: L = 50, V= 5, III = 3.
 *
 * Example 5:
 * Input: s = "MCMXCIV"
 * Output: 1994
 * Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.
 *
 * Constraints:
 * 1 <= s.length <= 15
 * s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
 * It is guaranteed that s is a valid roman numeral in the range [1, 3999].
 */

public class RomanToInteger {
    //用HashMap直接把所有情况罗列出来，最简单直接
    public int romanToInt(String s) {
        Map<String, Integer> map = new HashMap<>();
        map.put("I", 1);
        map.put("V", 5);
        map.put("X", 10);
        map.put("L", 50);
        map.put("C", 100);
        map.put("D", 500);
        map.put("M", 1000);
        map.put("IV", 4);
        map.put("IX", 9);
        map.put("XL", 40);
        map.put("XC", 90);
        map.put("CD", 400);
        map.put("CM", 900);
        int res = 0;

        for(int i=0; i<s.length(); i++){
            if((s.charAt(i)=='I' || s.charAt(i)=='X' || s.charAt(i)=='C') && i+1<s.length() && map.containsKey(s.substring(i, i+2))){
                res += map.get(s.substring(i, i+2));
                i++;
            }
            else{
                res += map.get(s.substring(i, i+1));
            }
        }
        return res;
    }

    //下面这种写法执行效率更好，但不简洁
    public int romanToInt2(String s) {
        int sum = 0;
        for(int i=0; i<s.length(); i++){
            if(subCase(i, s)){
                sum -= getValue(s.charAt(i));
            }
            else{
                sum += getValue(s.charAt(i));
            }
        }
        return sum;
    }

    private int getValue(char c){
        int value;
        switch(c){
            case 'I': value = 1;
                break;
            case 'V': value = 5;
                break;
            case 'X': value = 10;
                break;
            case 'L': value = 50;
                break;
            case 'C': value = 100;
                break;
            case 'D': value = 500;
                break;
            case 'M': value = 1000;
                break;
            default: value = 0;
                break;
        }
        return value;
    }

    private boolean subCase(int pos, String s){
        if(pos<s.length()-1){
            if(s.charAt(pos)=='I'){
                return s.charAt(pos+1)=='V' || s.charAt(pos+1)=='X';
            }
            if(s.charAt(pos)=='X'){
                return s.charAt(pos+1)=='L' || s.charAt(pos+1)=='C';
            }
            if(s.charAt(pos)=='C'){
                return s.charAt(pos+1)=='D' || s.charAt(pos+1)=='M';
            }
        }
        return false;
    }
}

