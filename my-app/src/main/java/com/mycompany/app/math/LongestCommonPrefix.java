package com.mycompany.app.math;
import java.util.*;

/**
 * https://leetcode.com/problems/longest-common-prefix/
 * Write a function to find the longest common prefix string amongst an array of strings.
 * If there is no common prefix, return an empty string "".
 *
 * Example 1:
 * Input: strs = ["flower","flow","flight"]
 * Output: "fl"
 *
 * Example 2:
 * Input: strs = ["dog","racecar","car"]
 * Output: ""
 * Explanation: There is no common prefix among the input strings.
 *
 * Constraints:
 * 0 <= strs.length <= 200
 * 0 <= strs[i].length <= 200
 * strs[i] consists of only lower-case English letters.
 *
 */

public class LongestCommonPrefix{
    public String longestCommonPrefix(String[] strs) {
        StringBuilder buf = new StringBuilder();
        boolean exit = false;
        for(int i=0; i<strs[0].length(); i++){
            for(int j=1; j<strs.length; j++){
                if(i>=strs[j].length() || strs[j].charAt(i)!=strs[0].charAt(i)){
                    exit = true;
                    break;
                }
            }
            if(!exit){
                buf.append(strs[0].charAt(i));
            }
            else{
                break;
            }
        }
        return buf.toString();
    }

    public String longestCommonPrefixSln2(String[] strs) {
        if(strs==null || strs.length==0){
            return "";
        }
        StringBuilder buf = new StringBuilder();
        buf.append(strs[0]);
        for(int i=1; i<strs.length; i++){
            int ptr = 0;
            while(ptr<strs[i].length() && ptr<buf.length() && buf.charAt(ptr)==strs[i].charAt(ptr)){
                ptr++;
            }
            buf.setLength(ptr);
        }
        return buf.toString();
    }
}