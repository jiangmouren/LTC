package com.mycompany.app;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/longest-substring-without-repeating-characters/
 * Given a string s, find the length of the longest substring without repeating characters.
 *
 * Example 1:
 * Input: s = "abcabcbb"
 * Output: 3
 * Explanation: The answer is "abc", with the length of 3.
 *
 * Example 2:
 * Input: s = "bbbbb"
 * Output: 1
 * Explanation: The answer is "b", with the length of 1.
 *
 * Example 3:
 * Input: s = "pwwkew"
 * Output: 3
 * Explanation: The answer is "wke", with the length of 3.
 * Notice that the answer must be a substring, "pwke" is a subsequence and not a substring.
 *
 * Example 4:
 * Input: s = ""
 * Output: 0
 *
 * Constraints:
 * 0 <= s.length <= 5 * 104
 * s consists of English letters, digits, symbols and spaces.
 */

/**
 * Analysis:
 * 这题目有点2-Sum的进阶版的感觉，尤其hashmap的使用思路，
 * 用来进行任意两个element之间的比较：在放进去之前一刻实现跟所有之前的比较，在放进去之后能够跟所有之后的比较。
 * 只不过此题在流程控制上更加复杂一些。
 * 把问题转化成了寻找两个dup的问题。实现上也有很多细节要注意。
 * 记忆这道题！！！
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int ptr0 = 0;
        int ptr1 = 0;
        while(ptr1<s.length()){
            char ptr1Char = s.charAt(ptr1);
            //reset ptr0
            //The reason we need to do Math.max() is because every update ptr0, we did not remove chars before ptr0 from the map
            //Thus, when there is a map hit, it is not necessarily a hit with a char after ptr0
            if(map.containsKey(ptr1Char)){
                ptr0 = Math.max(ptr0, (map.get(ptr1Char)+1));
            }
            maxLength = Math.max(maxLength, ptr1-ptr0+1);
            map.put(ptr1Char, ptr1);
            ptr1++;
        }
        return maxLength;
    }
}
