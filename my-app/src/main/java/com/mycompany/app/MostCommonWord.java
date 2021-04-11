package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/most-common-word/
 * Given a string paragraph and a string array of the banned words banned,
 * return the most frequent word that is not banned.
 * It is guaranteed there is at least one word that is not banned, and that the answer is unique.
 * The words in paragraph are case-insensitive and the answer should be returned in lowercase.
 *
 * Example 1:
 * Input: paragraph = "Bob hit a ball, the hit BALL flew far after it was hit.", banned = ["hit"]
 * Output: "ball"
 * Explanation:
 * "hit" occurs 3 times, but it is a banned word.
 * "ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
 * Note that words in the paragraph are not case sensitive,
 * that punctuation is ignored (even if adjacent to words, such as "ball,"),
 * and that "hit" isn't the answer even though it occurs more because it is banned.
 *
 * Example 2:
 * Input: paragraph = "a.", banned = []
 * Output: "a"
 *
 * Constraints:
 * 1 <= paragraph.length <= 1000
 * paragraph consists of English letters, space ' ', or one of the symbols: "!?',;.".
 * 0 <= banned.length <= 100
 * 1 <= banned[i].length <= 10
 * banned[i] consists of only lowercase English letters.
 */
//这就是个engineering的题，没什么算法，主要是考察处理edge case logic的能力
public class MostCommonWord {
    public String mostCommonWord(String paragraph, String[] banned) {
        String[] tokens = tokenize(paragraph);
        Set<String> set = new HashSet<>();
        for(String word : banned){
            set.add(word);
        }
        Map<String, Integer> map = new HashMap<>();
        int maxCnt = 0;
        String res = "";
        for(String token : tokens){
            //System.out.println(token);
            if(!set.contains(token)){
                if(map.containsKey(token)){
                    int cnt = map.get(token);
                    map.put(token, ++cnt);
                    if(cnt>maxCnt){
                        maxCnt = cnt;
                        res = token;
                    }
                }
                else{
                    map.put(token, 1);
                    if(1>maxCnt){
                        maxCnt = 1;
                        res = token;
                    }
                }
            }
        }
        return res;
    }

    private String[] tokenize(String paragraph){
        StringBuilder buf = new StringBuilder();
        for(int i=0; i<paragraph.length(); i++){
            char c = paragraph.charAt(i);
            if(c=='!' || c=='?' || c=='\'' || c==',' || c=='.'){
                buf.append(" ");
            }
            else{
                buf.append(c);
            }
        }
        String[] tokens = buf.toString().split(" +");
        buf.setLength(0);
        for(int j=0; j<tokens.length; j++){
            String token = tokens[j];
            for(int i=0; i<token.length(); i++){
                if(Character.isLetter(token.charAt(i))){
                    buf.append(Character.toLowerCase(token.charAt(i)));
                }
            }
            tokens[j] = buf.toString();
            buf.setLength(0);
        }
        return tokens;
    }
}
