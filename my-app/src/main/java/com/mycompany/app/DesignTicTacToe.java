package com.mycompany.app;

/**
 * https://leetcode.com/problems/design-tic-tac-toe/
 * Assume the following rules are for the tic-tac-toe game on an n x n board between two players:
 *
 * A move is guaranteed to be valid and is placed on an empty block.
 * Once a winning condition is reached, no more moves are allowed.
 * A player who succeeds in placing n of their marks in a horizontal, vertical, or diagonal row wins the game.
 * Implement the TicTacToe class:
 *
 * TicTacToe(int n) Initializes the object the size of the board n.
 * int move(int row, int col, int player) Indicates that player with id player plays at the cell (row, col) of the board.
 * The move is guaranteed to be a valid move.
 * Follow up:
 * Could you do better than O(n2) per move() operation?
 *
 * Example 1:
 *
 * Input
 * ["TicTacToe", "move", "move", "move", "move", "move", "move", "move"]
 * [[3], [0, 0, 1], [0, 2, 2], [2, 2, 1], [1, 1, 2], [2, 0, 1], [1, 0, 2], [2, 1, 1]]
 * Output
 * [null, 0, 0, 0, 0, 0, 0, 1]
 *
 * Explanation
 * TicTacToe ticTacToe = new TicTacToe(3);
 * Assume that player 1 is "X" and player 2 is "O" in the board.
 * ticTacToe.move(0, 0, 1); // return 0 (no one wins)
 * |X| | |
 * | | | |    // Player 1 makes a move at (0, 0).
 * | | | |
 *
 * ticTacToe.move(0, 2, 2); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 2 makes a move at (0, 2).
 * | | | |
 *
 * ticTacToe.move(2, 2, 1); // return 0 (no one wins)
 * |X| |O|
 * | | | |    // Player 1 makes a move at (2, 2).
 * | | |X|
 *
 * ticTacToe.move(1, 1, 2); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 2 makes a move at (1, 1).
 * | | |X|
 *
 * ticTacToe.move(2, 0, 1); // return 0 (no one wins)
 * |X| |O|
 * | |O| |    // Player 1 makes a move at (2, 0).
 * |X| |X|
 *
 * ticTacToe.move(1, 0, 2); // return 0 (no one wins)
 * |X| |O|
 * |O|O| |    // Player 2 makes a move at (1, 0).
 * |X| |X|
 *
 * ticTacToe.move(2, 1, 1); // return 1 (player 1 wins)
 * |X| |O|
 * |O|O| |    // Player 1 makes a move at (2, 1).
 * |X|X|X|
 *
 *
 * Constraints:
 * 2 <= n <= 100
 * player is 1 or 2.
 * 1 <= row, col <= n
 * (row, col) are unique for each different call to move.
 * At most n2 calls will be made to move.
 */
public class DesignTicTacToe {
    int[] r0;//per row cnt for player1
    int[] c0;//per col cnt for player1
    int[] r1;//per row cnt for player2
    int[] c1;//per col cnt for player2
    int[] md0;//main diagnal cnt for play1
    int[] md1;//main diagnal cnt for play2
    int[] sd0;//secd diagnal cnt for play1
    int[] sd1;//secd diagnal cnt for play2

    /** Initialize your data structure here. */
    public DesignTicTacToe(int n) {
        this.r0 = new int[n];
        this.r1 = new int[n];
        this.c0 = new int[n];
        this.c1 = new int[n];
        this.md0 = new int[]{0};
        this.md1 = new int[]{0};
        this.sd0 = new int[]{0};
        this.sd1 = new int[]{0};
    }

    /** Player {player} makes a move at ({row}, {col}).
     @param row The row of the board.
     @param col The column of the board.
     @param player The player, can be either 1 or 2.
     @return The current winning condition, can be either:
     0: No one wins.
     1: Player 1 wins.
     2: Player 2 wins. */
    public int move(int row, int col, int player) {
        if(player==1){
            if(populate(r0, c0, md0, sd0, row, col)){
                return 1;
            }
            else{
                return 0;
            }
        }
        else{
            //System.out.println(md1);
            if(populate(r1, c1, md1, sd1, row, col)){
                return 2;
            }
            else{
                return 0;
            }
        }

    }

    private boolean populate(int[] r, int[] c, int[] md, int[] sd, int row, int col){
        int n = r.length;
        r[row]++;
        if(r[row]==n){
            return true;
        }
        c[col]++;
        if(c[col]==n){
            return true;
        }
        if(row==col){
            //System.out.println("Here");
            md[0]++;
            if(md[0]==n){
                return true;
            }
        }
        if(row+col==n-1){
            sd[0]++;
            if(sd[0]==n){
                return true;
            }
        }

        return false;
    }
}
