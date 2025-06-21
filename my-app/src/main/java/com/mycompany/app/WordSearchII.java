package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/word-search-ii/
 * Given an m x n board of characters and a list of strings words, return all words on the board.
 *
 * Each word must be constructed from letters of sequentially adjacent cells,
 * where adjacent cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once in a word.
 *
 * Example 1:
 * Input: board = [["o","a","a","n"],["e","t","a","e"],["i","h","k","r"],["i","f","l","v"]], words = ["oath","pea","eat","rain"]
 * Output: ["eat","oath"]
 *
 * Example 2:
 * Input: board = [["a","b"],["c","d"]], words = ["abcb"]
 * Output: []
 *
 *
 * Constraints:
 *
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 12
 * board[i][j] is a lowercase English letter.
 * 1 <= words.length <= 3 * 10^4
 * 1 <= words[i].length <= 10
 * words[i] consists of lowercase English letters.
 * All the strings of words are unique.
 */
//一道结合了Trie & DFS/backtracking的题，代码量不小，思路并不复杂
public class WordSearchII{
    public List<String> findWords(char[][] board, String[] words) {
        Node dummyHead = buildTrie(words);
        StringBuilder buf = new StringBuilder();
        List<String> res = new ArrayList<>();
        Set<String> set = new HashSet<>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(dummyHead.map.containsKey(board[i][j])){
                    dfs(board, i, j, dummyHead.map.get(board[i][j]), buf, set, visited);
                }
            }
        }
        res.addAll(set);
        return res;
    }

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

    private Node buildTrie(String[] words){
        Node dummyHead = new Node('0');
        for(String word : words){
            insert(dummyHead, word);
        }
        return dummyHead;
    }

    private void insert(Node head, String word){
        Node ptr = head;
        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);
            if(!ptr.map.containsKey(c)){
                Node child = new Node(c);
                ptr.map.put(c, child);
            }
            ptr = ptr.map.get(c);
        }
        ptr.end = true;
    }

    private void dfs(char[][] board, int x, int y, Node node, StringBuilder buf, Set<String> res, boolean[][] visited){
        buf.append(board[x][y]);
        visited[x][y] = true;
        if(node.end){
            res.add(buf.toString());
        }
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && yNew>=0 && xNew<board.length && yNew<board[0].length && !visited[xNew][yNew] && node.map.containsKey(board[xNew][yNew])){
                dfs(board, xNew, yNew, node.map.get(board[xNew][yNew]), buf, res, visited);
            }
        }
        buf.deleteCharAt(buf.length()-1);
        visited[x][y] = false;
    }
}
