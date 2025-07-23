package com.mycompany.app.graph;
import java.util.*;
/**
 * https://leetcode.com/problems/similar-string-groups/
 * Two strings X and Y are similar if we can swap two letters (in different positions) of X,
 * so that it equals Y. Also two strings X and Y are similar if they are equal.
 * For example, "tars" and "rats" are similar (swapping at positions 0 and 2),
 * and "rats" and "arts" are similar, but "star" is not similar to "tars", "rats", or "arts".
 *
 * Together, these form two connected groups by similarity: {"tars", "rats", "arts"} and {"star"}.
 * Notice that "tars" and "arts" are in the same group even though they are not similar.
 * Formally, each group is such that a word is in the group if and only if it is similar to at least one other word in the group.
 * We are given a list strs of strings where every string in strs is an anagram of every other string in strs.
 * How many groups are there?
 *
 * Example 1:
 * Input: strs = ["tars","rats","arts","star"]
 * Output: 2
 *
 * Example 2:
 * Input: strs = ["omv","ovm"]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= strs.length <= 300
 * 1 <= strs[i].length <= 300
 * strs[i] consists of lowercase letters only.
 * All words in strs have the same length and are anagrams of each other
 */
//下面这种接法的复杂度为O(k*n^2). k is the str[i].length; n is strs.length.
public class SimilarStringGroups {
    public int numSimilarGroups(String[] strs) {
        Map<String, List<String>> graph = buildGraph(strs);
        int cnt = 0;
        Set<String> visit = new HashSet<>();
        for(String str : strs){
            if(!visit.contains(str)){
                cnt++;
                dfs(graph, str, visit);
            }
        }
        return cnt;
    }

    private void dfs(Map<String, List<String>> graph, String root, Set<String> visit){
        visit.add(root);
        for(String nbor : graph.get(root)){
            if(!visit.contains(nbor)){
                dfs(graph, nbor, visit);
            }
        }
    }

    //最开始的时候，我的写法是把所有的nbor全都生成，然后去跟strs里面的去比较，这样的复杂度是O(nk^3)
    //有k^2个nbor需要生成，生成每个nbor是O(k)，所以单是生成nbors就O(k^3)
    //上面这种想法不能满足leetcode的时间要求
    private Map<String, List<String>> buildGraph(String[] strs){
        Map<String, List<String>> map = new HashMap<>();
        for(String str : strs){
            map.put(str, new ArrayList<String>());
        }

        for(String s : strs){
            for(String t : strs){
                if(s!=t && check(s, t)){
                    map.get(s).add(t);
                }
            }
        }
        return map;
    }

    private boolean check(String s,String t){
        int res = 0, i = 0;
        while(res <= 2 && i < s.length()){
            if(s.charAt(i) != t.charAt(i)) res++;
            i++;
        }
        return res == 2;
    }
}
