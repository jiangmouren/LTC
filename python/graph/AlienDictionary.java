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
            return "";
        }

        int[] visit = new int[26];
        StringBuilder res = new StringBuilder();
        for(char key : graph.keySet()){
            if(visit[key-'a']==0){
                if(!dfs(res, graph, visit, key)){
                    return "";
                }
            }
        }
        return res.reverse().toString();
    }

    //return null if the inputs are not valid for example ["abc", "ab"]
    private Map<Character, List<Character>> buildGraph(String[] words){
        Map<Character, List<Character>> map = new HashMap<>();
        //要给每个Node都生成一个entry，而不是只处理edge情况，如果给的图就是一堆node，没有edge，就出问题了
        for(String word : words){
            populateMap(word, map);
        }

        int ptr0 = 0;
        int ptr1 = 1;
        while(ptr1<words.length){
            List<Character> pair = findDiff(words[ptr0], words[ptr1]);
            if(!pair.isEmpty()){
                if(!map.containsKey(pair.get(0))){
                    List<Character> list = new ArrayList<>();
                    map.put(pair.get(0), list);
                }
                map.get(pair.get(0)).add(pair.get(1));
            }
            else{
                if(words[ptr0].length()>words[ptr1].length()){
                    return null;
                }
            }
            ptr0++;
            ptr1++;
        }
        return map;
    }

    //return edge start and end, return empty list if no edge can be identified.
    private List<Character> findDiff(String word0, String word1){
        int ptr0 = 0;
        int ptr1 = 0;
        List<Character> res = new ArrayList<>();
        while(ptr0<word0.length() && ptr1<word1.length()){
            if(word0.charAt(ptr0)!=word1.charAt(ptr1)){
                res.add(word0.charAt(ptr0));
                res.add(word1.charAt(ptr1));
                break;
            }
            ptr0++;
            ptr1++;
        }
        return res;
    }

    private boolean dfs(StringBuilder res, Map<Character, List<Character>> graph, int[] visit, char root){
        visit[root-'a'] = 1;//0: not visited; 1: visiting; 2: visited
        //System.out.println(root);
        List<Character> children = graph.get(root);
        if(children!=null && !children.isEmpty()){
            for(char child : children){
                if(visit[child - 'a']==1){
                    return false;
                }
                if(visit[child-'a']==0){
                    if(!dfs(res, graph, visit, child)){
                        return false;
                    }
                }
            }
        }
        res.append(root);
        visit[root-'a'] = 2;
        return true;
    }

    private void populateMap(String word, Map<Character, List<Character>> map){
        for(int i=0; i<word.length(); i++){
            if(!map.containsKey(word.charAt(i))){
                List<Character> list = new ArrayList<>();
                map.put(word.charAt(i), list);
            }
        }
    }
}

