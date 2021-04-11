package com.mycompany.app;

import java.util.*;

/**
 * https://leetcode.com/problems/design-add-and-search-words-data-structure/
 * Design a data structure that supports adding new words and finding if a string matches any previously added string.
 * Implement the WordDictionary class:
 * WordDictionary() Initializes the object.
 * void addWord(word) Adds word to the data structure, it can be matched later.
 * bool search(word) Returns true if there is any string in the data structure that matches word or false otherwise.
 * word may contain dots '.' where dots can be matched with any letter.
 *
 * Example:
 * Input
 * ["WordDictionary","addWord","addWord","addWord","search","search","search","search"]
 * [[],["bad"],["dad"],["mad"],["pad"],["bad"],[".ad"],["b.."]]
 * Output
 * [null,null,null,null,false,true,true,true]
 * Explanation
 * WordDictionary wordDictionary = new WordDictionary();
 * wordDictionary.addWord("bad");
 * wordDictionary.addWord("dad");
 * wordDictionary.addWord("mad");
 * wordDictionary.search("pad"); // return False
 * wordDictionary.search("bad"); // return True
 * wordDictionary.search(".ad"); // return True
 * wordDictionary.search("b.."); // return True
 *
 * Constraints:
 * 1 <= word.length <= 500
 * word in addWord consists lower-case English letters.
 * word in search consist of  '.' or lower-case English letters.
 * At most 50000 calls will be made to addWord and search.
 */
public class DesignAddAndSearchWordsDataStructure {
    //这是一个Trie的应用问题，题目中"any string in the data structure that matches word"的描述不准确，应该是any word
    class Node{
        Character val;
        Map<Character, Node> map;//可以用Node[] = new Node[26]来优化performance
        boolean end;
        public Node(Character val){
            this.val = val;
            this.map = new HashMap<>();
        }
    }

    Node root;

    /** Initialize your data structure here. */
    public DesignAddAndSearchWordsDataStructure() {
        this.root = new Node('*');
    }

    public void addWord(String word) {
        Node ptr = root;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!ptr.map.containsKey(c)){
                Node node = new Node(c);
                ptr.map.put(c, node);
                ptr = node;
            }
            else{
                ptr = ptr.map.get(c);
            }
        }
        ptr.end = true;
    }

    public boolean search(String word) {
        return backtracking(word, 0, this.root);
    }

    private boolean backtracking(String word, int start, Node root){
        //termination
        if(start>=word.length()){
            return root.end;
        }

        if(root.map.containsKey(word.charAt(start))){
            return backtracking(word, start+1, root.map.get(word.charAt(start)));
        }
        else if(word.charAt(start)=='.'){
            for(Map.Entry<Character, Node> entry : root.map.entrySet()){
                if(backtracking(word, start+1, entry.getValue())){
                    return true;
                }
            }
            return false;
        }
        else{
            return false;
        }
    }
}
