package com.mycompany.app;

/**
 * https://leetcode.com/problems/verifying-an-alien-dictionary/
 * In an alien language, surprisingly they also use english lowercase letters, but possibly in a different order. The order of the alphabet is some permutation of lowercase letters.
 * Given a sequence of words written in the alien language, and the order of the alphabet, return true if and only if the given words are sorted lexicographicaly in this alien language.
 *
 * Example 1:
 * Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
 * Output: true
 * Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.
 *
 * Example 2:
 * Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
 * Output: false
 * Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.
 *
 * Example 3:
 * Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
 * Output: false
 * Explanation: The first three characters "app" match, and the second string is shorter (in size.) According to lexicographical rules "apple" > "app", because 'l' > '∅', where '∅' is defined as the blank character which is less than any other character (More info).
 *
 *
 * Constraints:
 * 1 <= words.length <= 100
 * 1 <= words[i].length <= 20
 * order.length == 26
 * All characters in words[i] and order are English lowercase letters.
 */
public class VerifyingAlienDictionary {
    public boolean isAlienSorted(String[] words, String order) {
        int[] map = new int[26];
        for(int i=0; i<order.length(); i++){
            map[order.charAt(i)-'a'] = i;
        }
        for(int i=0; i<words.length-1; i++){
            if(compare(words[i], words[i+1], map)>0){
                return false;
            }
        }
        return true;
    }

    //return: 0 if word1==word2; 1 if word1>word2; -1 if word1<word2
    private int compare(String word1, String word2, int[] map){
        int ptr=0;
        while(ptr<word1.length() && ptr<word2.length()){
            int c1 = word1.charAt(ptr)-'a';
            int c2 = word2.charAt(ptr)-'a';
            if(map[c1]<map[c2]){
                return -1;
            }
            if(map[c1]>map[c2]){
                return 1;
            }
            ptr++;
        }
        if(word1.length()<word2.length()){
            return -1;
        }
        else if(word1.length()>word2.length()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
