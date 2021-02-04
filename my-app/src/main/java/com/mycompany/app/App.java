package com.mycompany.app;
import java.util.*;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args){
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        String[] temp = {"a","aa","aaa","aaaa","aaaaa","aaaaaa","aaaaaaa","aaaaaaaa","aaaaaaaaa","aaaaaaaaaa"};
        List<String> wordDict = Arrays.asList(temp);
        App instance = new App();
        System.out.println(instance.wordBreak(s, wordDict));
    }

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
}