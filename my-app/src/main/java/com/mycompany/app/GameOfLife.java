/**
 * Question:
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life,
 * is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0).
 * Each cell interacts with its eight neighbors (horizontal, vertical, diagonal)
 * using the following four rules (taken from the above Wikipedia article):
 * Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 * Any live cell with two or three live neighbors lives on to the next generation.
 * Any live cell with more than three live neighbors dies, as if by over-population..
 * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 * Write a function to compute the next state (after one update) of the board given its current state.
 * Follow up:
 * Could you solve it in-place? Remember that the board needs to be updated at the same time:
 * You cannot update some cells first and then use their updated values to update other cells.
 * In this question, we represent the board using a 2D array.
 * In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array.
 * How would you address these problems?
 */

/**
 * Analysis:
 * The only catch for the 1st "State Transitioning" problem, is that remember the state transition should happen
 * in parallel. Basically cannot loop through and mix new state with old state.
 * To get around that we can either construct a new array, or
 * to encode state in a way it will represent both current the previous state.
 * The encoding should avoid overlap with original representation.
 *
 * The catch for the follow up question is that we need to redesign the object. It is a design question.
 *
 */
package com.mycompany.app;

public class GameOfLife{
    /**
     * Avoid 0 and 1
     * 2: (dead, dead)
     * 3: (dead, live)
     * 4: (live, dead)
     * 5: (live, live)
     * @param board
     */
    public void nextState(int[][] board){
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                board[i][j] = getNext(board, i, j);
            }
        }
        for(int i=0; i<board.length; i++){
            for(int j=0; j<board[0].length; j++){
                if(board[i][j]==2 || board[i][j]==4) board[i][j] = 0;
                else board[i][j] = 1;
            }
        }
        return;
    }

    private int getNext(int[][] board, int x, int y){
        int cnt = 0;
        int yLen = board.length;
        int xLen = board[0].length;
        int result;
        //left
        if(y-1>0 && board[x][y-1]==1 || board[x][y-1]==4 || board[x][y-1]==5) cnt++;
        //right
        if(y+1<xLen && board[x][y+1]==1 || board[x][y-1]==4 || board[x][y-1]==5) cnt++;
        //up
        if(x-1>0 && board[x-1][y]==1 || board[x][y-1]==4 || board[x][y-1]==5) cnt++;
        //down
        if(x+1<yLen && board[x+1][y]==1 || board[x][y-1]==4 || board[x][y-1]==5) cnt++;

        if(board[x][y]==1){
            if(cnt==2 || cnt==3) result = 5;
            else result = 4;
        }
        else{
            if(cnt==3) result = 3;
            else result = 2;
        }
        return result;
    }

    /**
     * Refer to the following link:
     * https://segmentfault.com/a/1190000003819277
     * To deal with the infinity, we need more than an 2-D array.
     * We need a Node class which will have 8 neighbors.
     * We will also need a boundary which
     */

}
