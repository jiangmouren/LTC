package com.mycompany.app.trie;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that supports the following two operations:

void addWord(word)
bool search(word)
search(word) can search a literal word or a regular expression string containing only letters a-z or .. A . means it can represent any one letter.

For example:

addWord("bad")
addWord("dad")
addWord("mad")
search("pad") -> false
search("bad") -> true
search(".ad") -> true
search("b..") -> true
Note:
You may assume that all words are consist of lowercase letters a-z.

click to show hint.

You should be familiar with how a Trie works. If not, please work on this problem: Implement Trie (Prefix Tree) first.
 TODO:
 */ 

public class AddandSearchWordDataStructureDesign {
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
    public AddandSearchWordDataStructureDesign() {
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

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
