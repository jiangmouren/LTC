package com.mycompany.app.hashMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Question: https://leetcode.com/problems/group-shifted-strings/
 * Given a string, we can "shift" each of its letter to its successive letter, for example: "abc" -> "bcd".
 * We can keep "shifting" which forms the sequence:
 * "abc" -> "bcd" -> ... -> "xyz"
 * Given a list of strings which contains only lowercase alphabets, group all strings that belong to the same shifting sequence.
 * For example, given: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"],
 * A solution is:
 * [
 *   ["abc","bcd","xyz"],
 *   ["az","ba"],
 *   ["acef"],
 *   ["a","z"]
 * ]
 */


public class GroupShiftedStrings {
    public List<List<String>> groupStrings(String[] strings) {
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strings){
            if(str.length()==1){
                if(!map.containsKey("a")){
                    map.put("a", new ArrayList<>());
                }
                map.get("a").add(str);
            }
            else{
                StringBuilder buf = new StringBuilder();
                for(int i=1; i<str.length(); i++){
                    int diff = str.charAt(i)-str.charAt(i-1);
                    diff = diff<0 ? diff + 26 : diff;//注意按照周期律，把差值全都normalize到[0, 25]
                    if(buf.length()>0){
                        buf.append("-");//注意这里要加个分割符，否则2+3根23就变成一样了
                    }
                    buf.append(diff);
                }
                String key = buf.toString();
                if(!map.containsKey(key)){
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(str);
            }
        }
        List<List<String>> res = new ArrayList<>();
        res.addAll(map.values());
        return res;
    }
}
