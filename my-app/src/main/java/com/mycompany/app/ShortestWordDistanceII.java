package com.mycompany.app;
/**
 * Question:
 * This is a follow up of Shortest Word Distance.
 * The only difference is now you are given the list of words and your method will be
 * called repeatedly many times with different parameters.
 * How would you optimize it?
 * Design a class which receives a list of words in the constructor,
 * and implements a method that takes two words word1 and word2 and return the shortest distance between these two words in the list.
 * For example,
 * Assume that words = ["practice", "makes", "perfect", "coding", "makes"].
 * Given word1 = “coding”, word2 = “practice”, return 3.
 * Given word1 = "makes", word2 = "coding", return 1.
 * Note:
 * You may assume that word1 does not equal to word2, and word1 and word2 are both in the list.
 * TODO:
 */


import java.util.*;

/**
 * Analysis:
 * Because we want to repeatedly call the function, we want to optimize the runtime for that.
 * Instead of looping through the whole list, if we can take out the positions of the relevant two words,
 * we can just loop through that small list.
 * We need a Map to pre-index this whole list.
 * Once we have that two list of potions, we can either loop through them separately, we just keep the larger pointer
 * fixed and move up the smaller pointer and update the min_value along the way. (Both lists need to be sorted)
 * Or we can create a new data type to hold the position and word pair, and we merge these two list into one.
 * We then sort that list and now we have a smaller problem of finding shortest distance.
 */

public class ShortestWordDistanceII{
    private Map<String, List<Integer>> map;
    public ShortestWordDistanceII(List<String> list){
        this.map = new HashMap<>();
        for(int i=0; i<list.size(); i++){
            String key = list.get(i);
            if(!map.containsKey(key)){
                List<Integer> tmpList = new ArrayList<>();
                map.put(key, tmpList);
            }
            map.get(key).add(i);
        }
    }
    //loop 2 lists
    public int find1(String word1, String word2){
        List<Integer> list1 = this.map.get(word1);
        List<Integer> list2 = this.map.get(word2);
        Collections.sort(list1);
        Collections.sort(list2);
        int minValue = Integer.MAX_VALUE;
        int ptr1 = 0, ptr2 = 0;
        //either ptr1 or ptr2 overflows, that means before that final move, it was smaller.
        while(ptr1<list1.size() && ptr2<list2.size()){
            int tmp = Math.abs(list2.get(ptr2) - list1.get(ptr1));
            minValue = Math.min(minValue, tmp);
            if(list1.get(ptr1)<list2.get(ptr2)){
                ptr1++;
            }
            else{
                ptr2++;
            }
        }
        return minValue;
    }

    //merge 2 lists
    public int find2(String word1, String word2){
        int minValue = Integer.MAX_VALUE;
        List<Integer> list1 = this.map.get(word1);
        List<Integer> list2 = this.map.get(word2);
        List<Node> nodeList1 = new ArrayList<>();
        List<Node> nodeList2 = new ArrayList<>();
        for(int i : list1){
            Node tmp = new Node(i, word1);
            nodeList1.add(tmp);
        }
        for(int i : list2){
            Node tmp = new Node(i, word2);
            nodeList2.add(tmp);
        }
        nodeList1.addAll(nodeList2);
        Collections.sort(nodeList1);
        int ptr1 = -1, ptr2 = -1;
        for(int i=0; i<nodeList1.size(); i++){
            if(nodeList1.get(i).str.equals(word1)){
                ptr1 = i;
                if(ptr2!=-1){
                    minValue = Math.min(minValue, Math.abs(nodeList1.get(ptr1).pos-nodeList1.get(ptr2).pos));
                }
            }
            if(nodeList2.get(i).str.equals(word2)){
                ptr2 = i;
                if(ptr1!=-1){
                    minValue = Math.min(minValue, Math.abs(nodeList1.get(ptr1).pos-nodeList1.get(ptr2).pos));
                }
            }
        }
        return minValue;
    }

    private class Node implements Comparable<Node>{
        int pos;
        String str;
        Node(int pos, String str){
            this.pos = pos;
            this.str = str;
        }

        @Override
        public int compareTo(Node o) {
            return this.pos-o.pos;
        }
    }
}
