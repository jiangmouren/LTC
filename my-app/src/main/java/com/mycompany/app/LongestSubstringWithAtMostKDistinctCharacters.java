package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
 * Given a string s and an integer k, return the length of the longest substring of s that contains at most k distinct characters.
 *
 * Example 1:
 * Input: s = "eceba", k = 2
 * Output: 3
 * Explanation: The substring is "ece" with length 3.
 *
 * Example 2:
 * Input: s = "aa", k = 1
 * Output: 2
 * Explanation: The substring is "aa" with length 2.
 *
 * Constraints:
 * 1 <= s.length <= 5 * 104
 * 0 <= k <= 50
 */

public class LongestSubstringWithAtMostKDistinctCharacters{
    //典型的sliding window题， map记录window里各个字母的count
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        if(k==0){
            return 0;
        }

        Map<Character, Integer> map = new HashMap<>();
        int ptr0 = 0;
        int ptr1 = 1;
        int max = 1;
        map.put(s.charAt(ptr0), 1);
        while(ptr1<s.length()){
            if(!map.containsKey(s.charAt(ptr1))){
                map.put(s.charAt(ptr1), 0);
            }
            map.put(s.charAt(ptr1), map.get(s.charAt(ptr1))+1);

            if(map.size()<=k){
                max = Math.max(max, ptr1-ptr0+1);
            }
            else{
                while(map.size()>k){
                    map.put(s.charAt(ptr0), map.get(s.charAt(ptr0))-1);
                    if(map.get(s.charAt(ptr0))==0){
                        map.remove(s.charAt(ptr0));
                    }
                    ptr0++;
                }
            }
            ptr1++;
        }
        return max;
    }
}