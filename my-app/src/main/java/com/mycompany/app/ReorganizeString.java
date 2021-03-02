package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/reorganize-string/
 * Given a string S, check if the letters can be rearranged so that two characters that are adjacent to each other are not the same.
 * If possible, output any possible result.  If not possible, return the empty string.
 *
 * Example 1:
 * Input: S = "aab"
 * Output: "aba"
 *
 * Example 2:
 * Input: S = "aaab"
 * Output: ""
 * Note:
 *
 * S will consist of lowercase letters and have length in range [1, 500].
 */

/**
 * 底下这种写法，在考虑lowercase letter only的情况下，map的size是固定小于26的，所以下面的复杂度是O(n)
 */
public class ReorganizeString {
    public String reorganizeString(String S) {
        if(S==null || S.length()==0){
            return "";
        }
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0; i<S.length(); i++){
            char c = S.charAt(i);
            if(!map.containsKey(c)){
                map.put(c, 1);
            }
            else{
                int cnt = map.get(c);
                map.put(c, ++cnt);
            }
        }
        Set<Map.Entry<Character, Integer>> set = map.entrySet();
        List<Map.Entry<Character, Integer>> list = new ArrayList<>();
        list.addAll(set);
        if(list.size()==1 && list.get(0).getValue()>1){
            return "";
        }
        Collections.sort(list, (a, b)->b.getValue()-a.getValue());
        //for(Map.Entry<Character, Integer> entry : list){
        //    System.out.print(entry.getKey()+": ");
        //    System.out.print(entry.getValue()+": ");
        //    System.out.println("");
        //}
        int maxCnt = list.get(0).getValue();
        char maxChar = list.get(0).getKey();
        //System.out.println("maxChar: "+maxChar);
        int sum = 0;
        for(int i=1; i<list.size(); i++){
            sum += list.get(i).getValue();
        }
        int totalCnt = sum + maxCnt;
        //System.out.println("sum: "+sum);
        //System.out.println("maxCnt: "+maxCnt);
        if(sum<maxCnt-1){
            return "";
        }
        int extraCnt = sum - maxCnt;
        //System.out.println("extraCnt: "+extraCnt);
        StringBuilder buf = new StringBuilder();
        while(totalCnt>0){
            String str;
            int actCnt;
            if(extraCnt>0){
                int requestCnt = extraCnt + 1;
                str = get(requestCnt, list);
                actCnt = str.length();
                extraCnt -= (actCnt-1);

            }
            else{
                str = get(1, list);
                //System.out.println("str: "+str);
                actCnt = str.length();
            }
            buf.append(maxChar);
            totalCnt--;
            buf.append(str);
            totalCnt -= actCnt;
        }
        return buf.toString();
    }

    private String get(int requestCnt, List<Map.Entry<Character, Integer>> list){
        StringBuilder buf = new StringBuilder();
        int sum = 0;
        for(int i=1; i<list.size() && sum<requestCnt; i++){
            Map.Entry<Character, Integer> entry = list.get(i);
            if(entry.getValue()>0){
                buf.append(entry.getKey());
                int cnt = entry.getValue() - 1;
                entry.setValue(cnt);
                sum++;
            }
        }
        return buf.toString();
    }
}
