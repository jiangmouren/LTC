package com.mycompany.app;

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
    public static void main(String[] args){
        MinimumWindowSubstring instance = new MinimumWindowSubstring();
        String s = "cabwefgewcwaefgcf";
        String t = "cae";
        System.out.println(instance.minWindow(s, t));
    }
    public String minWindow(String s, String t) {
        Map<Character, Queue<Integer>> sMap =  new HashMap<>();

        Map<Character, Integer> tMap = new HashMap<>();
        Set<Character> set = new HashSet<>();
        for(int i=0; i<t.length(); i++){
            char c = t.charAt(i);
            set.add(c);
            if(tMap.containsKey(c)){
                int val = tMap.get(c);
                tMap.put(c, ++val);
            }
            else{
                tMap.put(c, 1);
            }
        }

        int minLength = Integer.MAX_VALUE;
        int ptr = -1;
        int left = -1;
        int right = -1;

        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(tMap.containsKey(c)){
                if(ptr==-1){
                    ptr = i;
                }

                if(!sMap.containsKey(c)){
                    Queue<Integer> list = new LinkedList<Integer>();
                    sMap.put(c, list);
                }
                Queue<Integer> list = sMap.get(c);
                if(list.size()>=tMap.get(c)){
                    set.remove(c);
                    int val = list.remove();
                    list.add(i);
                    if(val==ptr){
                        while(ptr<=i){
                            if(!sMap.containsKey(s.charAt(ptr))){
                                ptr++;
                            }
                            else if(sMap.get(s.charAt(ptr)).peek()>ptr){
                                ptr++;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }
                else{
                    list.add(i);
                    if(list.size()==tMap.get(c)){
                        set.remove(c);
                    }
                }
                if(set.size()==0){
                    if(i-ptr<minLength){
                        right = i;
                        left = ptr;
                        minLength = right - left;
                    }
                }
            }
        }
        if(right==-1){
            return "";
        }
        else{
            return s.substring(left, right+1);
        }
    }
}