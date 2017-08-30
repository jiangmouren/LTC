package com.mycompany.app;
/**
 * Given two words (beginWord and endWord), and a dictionary's word list,
 * find the length of shortest transformation sequence from beginWord to endWord, such that:
 * Only one letter can be changed at a time.
 * Each transformed word must exist in the word list. Note that beginWord is not a transformed word.
 * For example,
 * Given:
 * beginWord = "hit"
 * endWord = "cog"
 * wordList = ["hot","dot","dog","lot","log","cog"]
 * As one shortest transformation is "hit" -> "hot" -> "dot" -> "dog" -> "cog",
 * return its length 5.
 * Note:
 * Return 0 if there is no such transformation sequence.
 * All words have the same length.
 * All words contain only lowercase alphabetic characters.
 * You may assume no duplicates in the word list.
 * You may assume beginWord and endWord are non-empty and are not the same.
 * UPDATE (2017/1/20):
 * The wordList parameter had been changed to a list of strings (instead of a set of strings).
 * Please reload the code definition to get the latest changes.
 * TODO:
 */

/**
 * A good backtracking problem.
 * There are two ways to write it.
 * At every step, you change one bit of at a time and see if the result is in the dictionary or not.
 * This way requires we put the wordlist into a Set.
 * Another way at every step, we loop through the wordlist and try to find a dif by one word, until find the target.
 * In the first approach, the complexity is (26k)^n, because at every step we have 26k options and total will be n steps.
 * In the second approach, the complexity is n^n, because at every step, n options and total n steps.
 */

import java.util.*;

public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> wordSet = new HashSet<>();
        wordSet.addAll(wordList);
        if(!wordSet.contains(endWord) || beginWord.equals(endWord)) return 0;

        StringBuilder buf = new StringBuilder();
        buf.append(beginWord);
        Set<String> pathSet = new HashSet<>();
        pathSet.add(beginWord);
        int[] minValue = {Integer.MAX_VALUE};
        helper(buf, endWord, wordSet, pathSet, minValue);
        if(minValue[0]==Integer.MAX_VALUE) return 0;
        else return minValue[0];
    }

    //StringBuilder inherited the equals from Object, so it won't really compare the content, reference comparison only.
    //To compare the content, either use toString function then compare, or match the length then match every char.
    private void helper(StringBuilder buf, String endWord, Set<String> wordSet, Set<String> pathSet, int[] minValue){
        //backtracking case
        if(compare(buf, endWord)){
            int tmp = pathSet.size()-1;
            minValue[0] = Math.min(tmp, minValue[0]);
            return;
        }

        //forward case
        //assume lower case only
        for(int i=0; i<buf.length(); i++){
            char oldCh = buf.charAt(i);
            for(char newCh='a'; newCh<'z'; newCh++){
                buf.setCharAt(i, newCh);
                String token = buf.toString();
                if(wordSet.contains(token) && !pathSet.contains(token)){
                    pathSet.add(token);
                    helper(buf, endWord, wordSet, pathSet, minValue);
                    pathSet.remove(pathSet.size()-1);
                }
            }
            buf.setCharAt(i, oldCh);
        }

    }

    private boolean compare(StringBuilder buf, String target){
        if(buf.length()!=target.length()) return false;
        else{
            for(int i=0; i<buf.length(); i++){
                if(buf.charAt(i)!=target.charAt(i)){
                    return false;
                }
            }
            return true;
        }
    }
}
