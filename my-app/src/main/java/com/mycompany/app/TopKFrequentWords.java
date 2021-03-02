package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-words/
 * Given a non-empty list of words, return the k most frequent elements.
 *
 * Your answer should be sorted by frequency from highest to lowest.
 * If two words have the same frequency, then the word with the lower alphabetical order comes first.
 *
 * Example 1:
 * Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
 * Output: ["i", "love"]
 * Explanation: "i" and "love" are the two most frequent words.
 *     Note that "i" comes before "love" due to a lower alphabetical order.
 *
 * Example 2:
 * Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
 * Output: ["the", "is", "sunny", "day"]
 * Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
 *     with the number of occurrence being 4, 3, 2 and 1 respectively.
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
 * Input words contain only lowercase letters.
 * Follow up:
 * Try to solve it in O(n log k) time and O(n) extra space.
 */

public class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        //比较麻烦的就是用quickSelect，这样能以O(n)找到k的位置，然后把按出现次数排序后面k个在sort一下，用时O(klogk)
        //所以这样总的复杂度是比O(nlogn)要好的
        //比较简单的写法就是用Heap
        Map<String, Integer> map = new HashMap<>();
        for(String word : words){
            if(!map.containsKey(word)){
                map.put(word, 1);
            }
            else{
                int cnt = map.get(word);
                map.put(word, ++cnt);
            }
        }
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(11, (a, b)->{
            int cmp = a.getValue() - b.getValue();
            if(cmp==0){
                //注意这里要反着写，alphabetical order小的，反倒要在compare的时候让它大，这样才能留在heap里
                cmp = b.getKey().compareTo(a.getKey());
                return cmp;
            }
            return cmp;
        });
        for(Map.Entry<String, Integer> entry : entrySet){
            minHeap.add(entry);
            if(minHeap.size()>k){
                minHeap.poll();
            }
        }
        List<String> res = new ArrayList<>();
        while(!minHeap.isEmpty()){
            res.add(minHeap.poll().getKey());
        }
        //最后要记得reverse一次，因为从heap里poll出来的，不管是cnt order还是alphabetical order全是反的。
        Collections.reverse(res);
        return res;
    }
}
