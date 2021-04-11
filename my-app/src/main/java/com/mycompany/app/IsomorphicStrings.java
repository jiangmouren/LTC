package com.mycompany.app;

import java.util.*;

/**
 * Question: https://leetcode.com/problems/isomorphic-strings/
 * Given two strings s and t, determine if they are isomorphic.
 * Two strings s and t are isomorphic if the characters in s can be replaced to get t.
 * All occurrences of a character must be replaced with another character while preserving the order of characters.
 * No two characters may map to the same character, but a character may map to itself.
 *
 * Example 1:
 * Input: s = "egg", t = "add"
 * Output: true
 *
 * Example 2:
 * Input: s = "foo", t = "bar"
 * Output: false
 *
 * Example 3:
 * Input: s = "paper", t = "title"
 * Output: true
 *
 * Constraints:
 * 1 <= s.length <= 5 * 104
 * t.length == s.length
 * s and t consist of any valid ascii character.
 */

public class IsomorphicStrings{
    //这个题的本质是要判断s & t的字母Pattern是否相同，所以就用两个HashMap把各自的pattern整理出来
    //然后比对两个pattern
    public boolean isIsomorphic(String s, String t) {
        int l = s.length();
        if(t.length() != l){
            return false;
        }

        Map<Character, List<Integer>> mapS = new HashMap<>();
        Map<Character, List<Integer>> mapT = new HashMap<>();
        for(int i=0; i<l; i++){
            char c1 = s.charAt(i);
            char c2 = t.charAt(i);
            if(mapS.containsKey(c1)){
                mapS.get(c1).add(i);
            }
            else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                mapS.put(c1, list);
            }
            if(mapT.containsKey(c2)){
                mapT.get(c2).add(i);
            }
            else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                mapT.put(c2, list);
            }
        }
        Set<List<Integer>> setS = new HashSet<>();
        setS.addAll(mapS.values());
        Set<List<Integer>> setT = new HashSet<>();
        setT.addAll(mapT.values());
        if(setS.size()!=setT.size()){
            return false;
        }
        for(List<Integer> list : setS){
            if(!setT.contains(list)){
                return false;
            }
        }
        return true;
    }
}
