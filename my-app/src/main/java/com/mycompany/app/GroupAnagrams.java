package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/group-anagrams/
 * Given an array of strings strs, group the anagrams together. You can return the answer in any order.
 * An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase,
 * typically using all the original letters exactly once.
 *
 * Example 1:
 * Input: strs = ["eat","tea","tan","ate","nat","bat"]
 * Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
 *
 * Example 2:
 * Input: strs = [""]
 * Output: [[""]]
 *
 * Example 3:
 * Input: strs = ["a"]
 * Output: [["a"]]
 *
 * Constraints:
 * 1 <= strs.length <= 104
 * 0 <= strs[i].length <= 100
 * strs[i] consists of lower-case English letters.
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        //面试这么写，平时一般避免用List type做hashKey，因位其mutable
        //具体这里可以转成String，再用作Key
        Map<List<Character>, List<String>> map = new HashMap<>();
        for(String str : strs){
            List<Character> buf = new ArrayList<>();
            for(int i=0; i<str.length(); i++){
                buf.add(str.charAt(i));
            }
            Collections.sort(buf);
            if(!map.containsKey(buf)){
                List<String> list = new ArrayList<>();
                list.add(str);
                map.put(buf, list);
            }
            else{
                map.get(buf).add(str);
            }
        }
        Collection<List<String>> values = map.values();
        List<List<String>> res = new ArrayList<>();
        res.addAll(values);
        return res;
    }
}

