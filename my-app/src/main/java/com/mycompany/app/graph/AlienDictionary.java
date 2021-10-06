package com.mycompany.app.graph;

import java.util.*;

/**
 * https://leetcode.com/problems/alien-dictionary/
 * There is a new alien language that uses the English alphabet.
 * However, the order among letters are unknown to you.
 * You are given a list of strings words from the dictionary,
 * where words are sorted lexicographically by the rules of this new language.
 * Derive the order of letters in this language, and return it.
 * If the given input is invalid, return "". If there are multiple valid solutions, return any of them.
 *
 * Example 1:
 * Input: words = ["wrt","wrf","er","ett","rftt"]
 * Output: "wertf"
 *
 * Example 2:
 * Input: words = ["z","x"]
 * Output: "zx"
 *
 * Example 3:
 * Input: words = ["z","x","z"]
 * Output: ""
 * Explanation: The order is invalid, so return "".
 *
 * Constraints:
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 100
 * words[i] consists of only lowercase English letters.
 */

//这本质上是一个topological sort的问题
class AlienDictionary {
    public String alienOrder(String[] words) {
        Map<Character, List<Character>> graph = buildGraph(words);
        if(graph==null){
            System.out.println("Here");
            return "";
        }
        //for(Map.Entry<Character, List<Character>> entry : graph.entrySet()){
        //    List<Character> values = entry.getValue();
        //    System.out.println("key: "+entry.getKey());
        //    for(char c : values){
        //        System.out.print(c + " ");
        //    }
        //    System.out.println("");
        //}
        StringBuilder res = new StringBuilder();
        int[] visit = new int[26];//0: not; 1: visiting; 2: visited
        boolean cycle = false;
        for(Map.Entry<Character,List<Character>> entry : graph.entrySet()){
            char c = entry.getKey();
            if(visit[c-'a']==0){
                if(!dfs(res, graph, c, visit)){
                    cycle = true;
                    break;
                }
            }
        }
        if(cycle){
            return "";
        }
        return res.reverse().toString();
    }

    private Map<Character, List<Character>> buildGraph(String[] words){
        Map<Character, List<Character>> graph = new HashMap<>();
        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr0<words.length){
            for(int i=0; i<words[ptr0].length(); i++){
                char key = words[ptr0].charAt(i);
                if(!graph.containsKey(key)){
                    graph.put(key, new ArrayList<Character>());
                }
            }
            if(ptr1<words.length){
                List<Character> list = findDiff(words[ptr0], words[ptr1]);
                //System.out.println(words[ptr0] + ", " + words[ptr1]);
                //System.out.println(list.get(0) + ", " + list.get(1));
                if(list==null){
                    return null;
                }
                if(!list.isEmpty()){
                    char key = list.get(0);
                    char value = list.get(1);
                    graph.get(key).add(value);
                }
            }
            ptr0++;
            ptr1++;
        }
        return graph;
    }

    //return a list, where first char is ahead of second char
    //if return empty list, means no order pair found
    private List<Character> findDiff(String word1, String word2){
        int ptr = 0;
        int l1 = word1.length();
        int l2 = word2.length();
        List<Character> list = new ArrayList<>();
        while(ptr<l1 && ptr<l2){
            if(word1.charAt(ptr)!=word2.charAt(ptr)){
                list.add(word1.charAt(ptr));
                list.add(word2.charAt(ptr));
                break;
            }
            ptr++;
        }
        //if l2 shorter and all chars matches, incorrect! for example: ["abc","ab"], we cannot detect this during dfs, need to find this during building graph.
        if(ptr>=l2 && ptr<l1){
            return null;
        }
        return list;
    }

    private boolean dfs(StringBuilder res, Map<Character, List<Character>> graph, char root, int[] visit){
        List<Character> children = graph.get(root);
        visit[root-'a'] = 1;
        //termination
        if(children.isEmpty()){
            res.append(root);
            visit[root-'a'] = 2;
            return true;
        }

        for(char c : children){
            if(visit[c-'a']==1){
                return false;
            }
            if(visit[c-'a']==0){
                if(!dfs(res, graph, c, visit)){
                    return false;
                }
            }
        }
        res.append(root);
        visit[root -'a'] = 2;
        return true;
    }
}

