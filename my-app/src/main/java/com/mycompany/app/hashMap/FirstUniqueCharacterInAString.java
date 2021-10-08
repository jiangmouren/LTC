package com.mycompany.app.hashMap;

import java.util.*;

/**
 * https://leetcode.com/problems/first-unique-character-in-a-string/
 * Given a string, find the first non-repeating character in it and return its index.
 * If it doesn't exist, return -1.
 *
 * Examples:
 * s = "leetcode"
 * return 0.
 * s = "loveleetcode"
 * return 2.
 *
 * Note: You may assume the string contains only lowercase English letters.
 */

public class FirstUniqueCharacterInAString{
    //可以用LinkedHashMap处理，或者像下面这样手动加List来保留顺序，map只存指向list的idx
    public int firstUniqChar(String s) {
        List<Integer> idxList = new ArrayList<>();
        List<Integer> cntList = new ArrayList<>();
        //Map<Character, Integer> map = new HashMap<>();
        int[] map = new int[26];//换成int[26]，让runtime好看一点
        for(int i=0; i<26; i++){
            map[i] = -1;
        }
        int n = s.length();
        for(int i=0; i<n; i++){
            //if(map.containsKey(s.charAt(i))){
            if(map[s.charAt(i)-'a']!=-1){
                //int idx = map.get(s.charAt(i));
                int idx = map[s.charAt(i)-'a'];
                int cnt = cntList.get(idx);
                cntList.set(idx, ++cnt);
            }
            else{
                //map.put(s.charAt(i), idxList.size());
                map[s.charAt(i)-'a'] = idxList.size();
                idxList.add(i);
                cntList.add(1);
            }
        }
        int res = -1;
        for(int i=0; i<cntList.size(); i++){
            if(cntList.get(i)==1){
                res = idxList.get(i);
                break;
            }
        }
        return res;
    }
}

