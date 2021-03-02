package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/word-break/
 * Given a non-empty string s and a dictionary wordDict containing a list of non-empty words, determine if s can be segmented into a space-separated sequence of one or more dictionary words.
 *
 * Note:
 * The same word in the dictionary may be reused multiple times in the segmentation.
 * You may assume the dictionary does not contain duplicate words.
 *
 * Example 1:
 * Input: s = "leetcode", wordDict = ["leet", "code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 *
 * Example 2:
 * Input: s = "applepenapple", wordDict = ["apple", "pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 *              Note that you are allowed to reuse a dictionary word.
 * Example 3:
 * Input: s = "catsandog", wordDict = ["cats", "dog", "sand", "and", "cat"]
 * Output: false
 *
 * Analysis: 算是经典的DP问题，分析见：src\main\resources\WordBreakComplexity.jpg
 */

public class WordBreak{
    //Top Down DP with Memo
    public boolean wordBreak(String s, List<String> wordDict) {
        Set<String> set = new HashSet<String>();
        int maxLength = 0;
        for(String word : wordDict){
            maxLength = Math.max(maxLength, word.length());
            set.add(word);
        }
        Map<Integer, Boolean> cache = new HashMap<Integer, Boolean>();
        return check(s, 0, set, maxLength, cache);
    }

    private boolean check(String s, int start, Set<String> dict, int maxL, Map<Integer, Boolean> cache){
        if(cache.containsKey(start)){
            return cache.get(start);
        }
        if(start>=s.length()){
            cache.put(start, true);
            return true;
        }

        int ptr = start + 1;
        while(ptr<=s.length() && ptr-start<=maxL){
            String temp = s.substring(start, ptr);
            if(dict.contains(temp)){
                if(check(s, ptr, dict, maxL, cache)){
                    cache.put(start, true);
                    return true;
                }
            }
            ptr++;
        }
        cache.put(start, false);
        return false;
    }

    //Bottom Up DP
    public boolean wordBreakSln2(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length()];//default is false
        //dp[i] = dp[i-1]s[i:i] || dp[i-2]s[i-1:i] ||...|| dp[i-L]s[i-L+1:i]
        //L is the maxLength of word
        int maxL = 0;
        Set<String> dict = new HashSet<>();
        for(String word : wordDict){
            maxL = Math.max(maxL, word.length());
            dict.add(word);
        }

        for(int i=0; i<s.length(); i++){
            for(int j=i-1; j>=i-maxL; j--){
                if(j<0){
                    if(dict.contains(s.substring(0, i+1))){
                        dp[i] = true;
                        continue;
                    }
                }
                else{
                    if(dp[j]&&dict.contains(s.substring(j+1, i+1))){
                        dp[i] = true;
                        continue;
                    }
                }
            }
        }
        return dp[s.length()-1];
    }
}

