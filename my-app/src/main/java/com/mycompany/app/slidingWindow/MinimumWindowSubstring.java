package com.mycompany.app.slidingWindow;

import java.util.*;

/**
Given a string S and a string T, find the minimum window in S which will contain all the characters in T in complexity O(n).

For example,
S = "ADOBECODEBANC"
T = "ABC"
Minimum window is "BANC".

Note:
If there is no such window in S that covers all characters in T, return the empty string "".

If there are multiple such windows, you are guaranteed that there will always be only one unique minimum window in S.

 *
 */

//class Solution {
//    public String minWindow(String s, String t) {
//
//    }
//}

public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        //left always point to a key
        //if right find a key, update the map, check if the key left pointing to has more than what we need
        //if yes, then move left to the next key location
        //if the key at the new left location has more than the number required for that key in the current window (keep track of using a hashmap)
        //we again moving left, until it stops at a key location that is no more than requried in the current window
        //left更新完了之后，可以做一个判断，目前在right找到新key之后，是否构成一个新的解
        Map<Character, Integer> tMap = new HashMap<>();
        Map<Character, Integer> sMap = new HashMap<>();
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = Integer.MAX_VALUE;

        //populate tMap and set
        for(int i=0; i<t.length(); i++){
            set.add(t.charAt(i));
            if(!tMap.containsKey(t.charAt(i))){
                tMap.put(t.charAt(i), 0);
            }
            int cnt = tMap.get(t.charAt(i));
            cnt++;
            tMap.put(t.charAt(i), cnt);
        }

        //sliding window
        int ptr = -1;
        for(int i=0; i<s.length(); i++){
            if(tMap.containsKey(s.charAt(i))){
                if(ptr==-1){
                    ptr = i;
                }
                if(!sMap.containsKey(s.charAt(i))){
                    sMap.put(s.charAt(i), 0);
                }
                int cnt = sMap.get(s.charAt(i));
                cnt++;
                sMap.put(s.charAt(i), cnt);
                if(sMap.get(s.charAt(i))>=tMap.get(s.charAt(i))){
                    set.remove(s.charAt(i));
                }
                while(ptr<i){
                    if(tMap.containsKey(s.charAt(ptr)) && sMap.get(s.charAt(ptr))<=tMap.get(s.charAt(ptr))){
                        break;
                    }
                    else{
                        if(tMap.containsKey(s.charAt(ptr))){
                            int temp = sMap.get(s.charAt(ptr));
                            temp--;
                            sMap.put(s.charAt(ptr), temp);
                        }
                        ptr++;
                    }
                }
                //因为我永远只会在有多余的key的情况下移动left ptr，从第一次找到解之后，[left, right]永远是有效解
                if(set.size()==0 && i-ptr<right-left){//new solution
                    //System.out.println("found solution");
                    left = ptr;
                    right = i;
                    //System.out.println("right: " + right);
                    //System.out.println("left: " + left);
                }
            }
        }

        if(right==Integer.MAX_VALUE){
            return "";
        }
        else{
            return s.substring(left, right+1);
        }
    }
}