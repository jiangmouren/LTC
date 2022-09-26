package com.mycompany.app.slidingWindow;

/**
 * Question: https://leetcode.com/problems/shortest-word-distance/
 * Given an array of strings wordsDict and two different strings that already exist in the array word1 and word2,
 * return the shortest distance between these two words in the list.
 *
 * Example 1:
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "coding", word2 = "practice"
 * Output: 3
 *
 * Example 2:
 * Input: wordsDict = ["practice", "makes", "perfect", "coding", "makes"], word1 = "makes", word2 = "coding"
 * Output: 1
 *
 * Constraints:
 * 1 <= wordsDict.length <= 3 * 104
 * 1 <= wordsDict[i].length <= 10
 * wordsDict[i] consists of lowercase English letters.
 * word1 and word2 are in wordsDict.
 * word1 != word2
 */

public class ShortestWordDistance {
    public int shortestDistance(String[] wordsDict, String word1, String word2) {
        //典型的sliding window问题
        //从左到右一旦找到其中一个word,分成以下3种情况：
        //1. 发现的第一个word，标记这个位置
        //2. 新发现的word跟之前标记位置的word相同，那么放弃之前的word(因为新的word会离后面可能的目标更近),将ptr移动上来
        //3. 新发现的word跟之前的标记位置的word不同，那么记录一次当下或的距离，跟之前的结果比较取最小，然后放弃之前的word,将ptr移动上来

        int res = Integer.MAX_VALUE;
        int ptr0 = -1;
        int ptr1 = 0;
        while(ptr1<wordsDict.length){
            if(wordsDict[ptr1].equals(word1) || wordsDict[ptr1].equals(word2)){
                if(ptr0==-1 || wordsDict[ptr0].equals(wordsDict[ptr1])){
                    ptr0 = ptr1;
                }
                else{
                    res = Math.min(res, ptr1-ptr0);
                    ptr0 = ptr1;
                }
            }
            ptr1++;
        }
        return res;
    }
}
