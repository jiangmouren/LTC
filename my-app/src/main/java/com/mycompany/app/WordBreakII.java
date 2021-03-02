package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/word-break-ii/
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words,
 * add spaces in s to construct a sentence where each word is a valid dictionary word.
 * Return all such possible sentences.
 * Note:
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 * Input:
 * s = "catsanddog"
 * wordDict = ["cat", "cats", "and", "sand", "dog"]
 * Output:
 * [
 *   "cats and dog",
 *   "cat sand dog"
 * ]
 *
 * Example 2:
 * Input:
 * s = "pineapplepenapple"
 * wordDict = ["apple", "pen", "applepen", "pine", "pineapple"]
 * Output:
 * [
 *   "pine apple pen apple",
 *   "pineapple pen apple",
 *   "pine applepen apple"
 * ]
 * Explanation: Note that you are allowed to reuse a dictionary word.
 *
 * Example 3:
 * Input:
 * s = "catsandog"
 * wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output:
 * []
 */

/**
 * 这个题目谈两点：
 * 1. 既可以用DP也可以backtracking，但是以为backtracking不存subproblem的解，而这个问题又会遇到很多重复的subproblem
 *    所以用DP是更好的选择。
 * 2. 注意下面被我comment掉的代码，都是我实际在LeetCode中debug时用到的。要注意不能一遇到Bug就copy到IDE里debug，
 *    这样会在实际应试的时候吃亏，上次Amazon的面试就是因为没能够在没有ide的情况下快读debug而导致因为一个小bug使得一题没pass tests.
 */
public class WordBreakII {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Set<String> dict = new HashSet<>();
        int maxL = 0;
        for(String word : wordDict){
            dict.add(word);
            maxL = Math.max(maxL, word.length());
        }
        Map<Integer, List<String>> map = new HashMap<>();
        List<String> res = helper(s, dict, 0, maxL, map);
        //for(Map.Entry<Integer, List<String>> entry : map.entrySet()){
        //    System.out.println(entry.getKey());
        //    if(entry.getValue()==null){
        //        System.out.print("null");
        //        System.out.println("");
        //    }
        //    else{
        //        for(String str : entry.getValue()){
        //            System.out.print(str + " ");
        //        }
        //        System.out.println("");
        //    }
        //
        //}
        return res==null ? new ArrayList<String>() : res;
    }

    //return the list of possible sentences for subproblems
    //return empty list if search reaches the end; return null if cannot move forward
    private List<String> helper(String s, Set<String> dict, int start, int maxL, Map<Integer, List<String>> map){
        List<String> res = new ArrayList<>();
        //termination
        if(start>=s.length()){
            return res;
        }

        if(map.containsKey(start)){
            return map.get(start);
        }

        StringBuilder buf = new StringBuilder();
        for(int i=start+1; i<=s.length() && i<=start+maxL; i++){//pay attention to s.length()
            String temp = s.substring(start, i);
            if(dict.contains(temp)){
                List<String> subRes = helper(s, dict, i, maxL, map);
                //if(start==7){
                //    System.out.println(temp);
                //    System.out.println(subRes);
                //}
                if(subRes==null){
                    continue;
                }
                else{
                    buf.append(temp);

                    if(subRes.isEmpty()){
                        res.add(buf.toString());
                    }
                    else{
                        int l = buf.length();
                        for(String token : subRes){
                            buf.append(" ");
                            buf.append(token);
                            res.add(buf.toString());
                            buf.setLength(l);
                        }
                    }
                }
            }
            buf.setLength(0);
        }
        if(res.isEmpty()){
            map.put(start, null);
            return null;
        }
        map.put(start, res);
        return res;
    }
}
