package com.mycompany.app;

/**
 * Question: https://leetcode.com/problems/game-of-life/
 * According to Wikipedia's article: "The Game of Life, also known simply as Life,
 * is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 *
 * The board is made up of an m x n grid of cells, where each cell has an initial state:
 * live (represented by a 1) or dead (represented by a 0).
 * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal)
 * using the following four rules (taken from the above Wikipedia article):
 *
 * Any live cell with fewer than two live neighbors dies as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population.
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * The next state is created by applying the above rules simultaneously to every cell in the current state,
 * where births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
 *
 * Example 1:
 * Input: board = [[0,1,0],[0,0,1],[1,1,1],[0,0,0]]
 * Output: [[0,0,0],[1,0,1],[0,1,1],[0,1,0]]
 *
 * Example 2:
 * Input: board = [[1,1],[1,0]]
 * Output: [[1,1],[1,1]]
 *
 * Constraints:
 * m == board.length
 * n == board[i].length
 * 1 <= m, n <= 25
 * board[i][j] is 0 or 1.
 *
 * Follow up:
 * Could you solve it in-place? Remember that the board needs to be updated simultaneously:
 * You cannot update some cells first and then use their updated values to update other cells.
 * In this question, we represent the board using a 2D array.
 * In principle, the board is infinite, which would cause problems when the active area encroaches upon the border of the array (i.e., live cells reach the border).
 * How would you address these problems?
 */

/**
 * Analysis:
 * 关于要in place的问题，下面的解法已经处理了，就是设计好一个比较合理的mapping关系就可以了
 * 关于infinite size的问题，就要从两个方面处理：首先不能把真个board存下，其次不可能全都fit in memory需要写上disk
 * 简单来说可以理解为，用一个HashSet，把所有值是1的位置全都记录下来,然后更新状态的时候，对每一个1的位置操作，判断周围有几个1，
 * 就是HashSet的lookup，然后对每个1周围的(8个位置)上的0，也要操作；这整个打的HashSet无法fit in Memory没关系，
 * 可以写回disk，这是一个很好的使用key-value Store(比如DynamoDB)的很好的use case.
 *
 */
public class GameOfLife{
    public void gameOfLife(int[][] board) {
        //update board to new state with mapping
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                update(board, i, j);
            }
        }
        //for(int i=0; i<board.length; i++){
        //    for(int j=0; j<board[0].length; j++){
        //        System.out.print(board[i][j]+" ");
        //    }
        //    System.out.println(" ");
        //}
        //remap back to (0, 1) domain
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(board[i][j]==2){
                    board[i][j] = 1;
                }
                else if(board[i][j]==3){
                    board[i][j] = 0;
                }
            }
        }
    }

    private void update(int[][] board, int x, int y){
        int[][] dirs = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        //get the number of live neighbors
        int cnt = 0;
        for(int[] dir : dirs){
            int xNew = x + dir[0];
            int yNew = y + dir[1];
            if(xNew>=0 && xNew<board.length && yNew>=0 && yNew<board[0].length && (board[xNew][yNew]==1 || board[xNew][yNew]==3)){
                cnt++;
            }
        }
        //update to the new state, with following mapping: 0->0 (0); 1->1 (1); 0->1 (2); 1->0 (3)
        if(board[x][y]==1){
            if(cnt<2 || cnt>3){
                board[x][y] = 3;
            }
        }
        else{
            if(cnt==3){
                board[x][y] = 2;
            }
        }
    }
}
