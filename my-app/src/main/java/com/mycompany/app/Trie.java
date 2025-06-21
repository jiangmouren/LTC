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
    class Node{
        char c;
        Map<Character, Node> map;
        boolean end;
        public Node(char c){
            this.c = c;
            this.map = new HashMap<>();
            this.end = false;
        }
    }

    Node dummyHead;

    public Trie() {
        this.dummyHead = new Node('0');
    }

    public void insert(String word) {
        Node ptr = dummyHead;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!ptr.map.containsKey(c)){
                Node child = new Node(c);
                ptr.map.put(c, child);
            }
            ptr = ptr.map.get(c);
        }
        ptr.end = true;//注意ptr指向上一个char,而不是当前char
    }

    public boolean search(String word) {
        Node ptr = dummyHead;
        for(int i=0; i<word.length(); i++){
            if(!ptr.map.containsKey(word.charAt(i))){
                return false;
            }
            else{
                ptr = ptr.map.get(word.charAt(i));
            }
        }
        return ptr.end==true;//注意ptr指向上一个char,而不是当前char
    }

    public boolean startsWith(String prefix) {
        Node ptr = dummyHead;
        for(int i=0; i<prefix.length(); i++){
            if(!ptr.map.containsKey(prefix.charAt(i))){
                return false;
            }
            else{
                ptr = ptr.map.get(prefix.charAt(i));
            }
        }
        return true;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
