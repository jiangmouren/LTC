package com.mycompany.app;

/**
 * https://leetcode.com/problems/word-search/
 * Given an m x n board and a word, find if the word exists in the grid.
 * The word can be constructed from letters of sequentially adjacent cells,
 * where "adjacent" cells are horizontally or vertically neighboring.
 * The same letter cell may not be used more than once.
 *
 * Example 1:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
 * Output: true
 *
 * Example 2:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"
 * Output: true
 *
 * Example 3:
 * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"
 * Output: false
 *
 * Constraints:
 * m == board.length
 * n = board[i].length
 * 1 <= m, n <= 200
 * 1 <= word.length <= 103
 * board and word consists only of lowercase and uppercase English letters.
 */

public class WordSearch{
    public boolean exist(char[][] board, String word) {
        boolean[][] visit = new boolean[board.length][board[0].length];
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(backtrack(board, word, visit, i, j, 0)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtrack(char[][] board, String word, boolean[][] visit, int startX, int startY, int pos){
        //termination不能这么写是因为，matrix可能会限制无法走到这一步，比如对于：[["a"]]， "a". 所以不能等到"下一步"再判断。
        //if(pos>=word.length()){
        //    return true;
        //}
        if(board[startX][startY] != word.charAt(pos)){
            return false;
        }
        else{
            if(pos>=word.length()-1){
                return true;
            }
            visit[startX][startY] = true;
            int[][] dircs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
            for(int[] dirc : dircs){
                int xNew = startX + dirc[0];
                int yNew = startY + dirc[1];
                if(xNew<board.length && xNew>=0 && yNew<board[0].length && yNew>=0 && !visit[xNew][yNew]){
                    if(backtrack(board, word, visit, xNew, yNew, pos+1)){
                        return true;
                    }
                }
            }
            visit[startX][startY] = false;//只关心visiting，所以一旦off path，就要reset
            return false;
        }
    }
}