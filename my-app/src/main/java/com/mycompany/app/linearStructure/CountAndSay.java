package com.mycompany.app.linearStructure;

/**
 * https://leetcode.com/problems/count-and-say/
 * The count-and-say sequence is the sequence of integers with the first five terms as following:
 *
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 * Given an integer n, generate the nth term of the count-and-say sequence.
 *
 * Note: Each term of the sequence of integers will be represented as a string.
 *
 * Example 1:
 * Input: 1
 * Output: "1"
 *
 * Example 2:
 * Input: 4
 * Output: "1211"
 *
 * Constraints:
 * 1 <= n <= 30
 */

public class CountAndSay {
    public String countAndSay(int n) {
        String res = "1";
        for(int i=1; i<n; i++){
            res = convert(res);
        }
        return res;
    }

    private String convert(String str){
        StringBuilder buf = new StringBuilder();
        int ptr0 = 0;
        int ptr1 = ptr0;
        while(ptr0<str.length()){
            while(ptr1<str.length()&&str.charAt(ptr0)==str.charAt(ptr1)){
                ptr1++;
            }
            int diff = ptr1 - ptr0;
            buf.append(diff);
            buf.append(str.charAt(ptr0));
            ptr0 = ptr1;
        }
        return buf.toString();
    }
}
