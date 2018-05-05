package com.mycompany.app;
import java.util.*;
/**
 * Implement a trie with insert, search, and startsWith methods.
 * Note:
 * You may assume that all inputs are consist of lowercase letters a-z.
 */

/**
 * Analysis:
 * Something to memorize.
 * Can be called as a prefix Tree, every unique prefix correspond to a unique path.
 */
public class Trie {
    Node head;

    /** Initialize your data structure here. */
    public Trie() {
        this.head = new Node('$');
    }
    
    /** Inserts a word into the trie. */
    public void insert(String word) {
        Node ptr = head;
        for(int i=0; i<word.length(); i++){
            char tmp = word.charAt(i);
            if(!ptr.map.containsKey(tmp)){
                ptr.map.put(tmp, new Node(tmp));
            }
            ptr = ptr.map.get(tmp);
            if(i==word.length()-1){
                ptr.tail = true;
            }
        }
    }
    
    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        return searchHelper(word, 0, this.head);
    }


    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return startWithHelper(prefix, 0, this.head);
    }

    private boolean searchHelper(String word, int pos, Node ptr){
        char key = word.charAt(pos);
        boolean containsKey = ptr.map.containsKey(key);

        //backtracking case
        if(pos==word.length()-1){
            return containsKey&&ptr.map.get(key).tail;
        }

        //forward case
        if(containsKey){
            return searchHelper(word, pos+1, ptr.map.get(key));
        }
        else{
            return false;
        }
    }

    private boolean startWithHelper(String word, int pos, Node ptr){
        char key = word.charAt(pos);
        boolean containsKey = ptr.map.containsKey(key);
        //backtracking case
        if(pos==word.length()-1){
            return containsKey;
        }

        //forward case
        if(containsKey){
            return startWithHelper(word, pos+1, ptr.map.get(key));
        }
        else{
            return false;
        }
    }

    private class Node{
        char val;
        Map<Character, Node> map;
        boolean tail = false;
        Node(char x){
            this.val = x;
            map = new HashMap<>();
        }
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
