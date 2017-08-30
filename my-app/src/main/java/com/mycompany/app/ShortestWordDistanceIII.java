package com.mycompany.app;

import java.util.*;

/**
 * Question:
 * This is a follow up of Shortest Word Distance.
 * The only difference is now word1 could be the same as word2.
 * Given a list of words and two words word1 and word2, return the shortest distance between these two words in the list.
 * word1 and word2 may be the same and they represent two individual words in the list.
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = “makes”, word2 = “coding”, return 1.
 * Given word1 = "makes", word2 = "makes", return 3.
 * Note:
 * You may assume word1 and word2 are both in the list.
 * TODO:
 */

public class ShortestWordDistanceIII{
    public int find(String[] words, String word1, String word2){
        int ptr1 = -1, ptr2 = -2, minValue = Integer.MAX_VALUE;
        if(word1.equals(word2)){
            for(int i=0; i<words.length; i++){
                if(words[i].equals(word1)){
                    ptr2 = ptr1;
                    ptr1 = i;
                    if(ptr2!=-1){
                        minValue = Math.min(minValue, Math.abs(ptr2-ptr1));
                    }
                }
            }
        }
        else{
            for(int i=0; i<words.length; i++){
                if(words[i].equals(word1)){
                    ptr1 = i;
                    if(ptr2!=-1){
                        minValue = Math.min(minValue, Math.abs(ptr2-ptr1));
                    }
                }
                else if(words[i].equals(word2)){
                    ptr2 = i;
                    if(ptr1!=-1){
                        minValue = Math.min(minValue, Math.abs(ptr2-ptr1));
                    }
                }
            }
        }
        return minValue;
    }
}
