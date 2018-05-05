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
import java.util.*;

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
        int xLen = board.length;
        int yLen = board[0].length;
        int result;
        //left
        if(y-1>=0 && (board[x][y-1]==1 || board[x][y-1]==4 || board[x][y-1]==5)) cnt++;
        //right
        if(y+1<yLen && (board[x][y+1]==1 || board[x][y+1]==4 || board[x][y+1]==5)) cnt++;
        //up
        if(x-1>=0 && (board[x-1][y]==1 || board[x-1][y]==4 || board[x-1][y]==5)) cnt++;
        //down
        if(x+1<xLen && (board[x+1][y]==1 || board[x+1][y]==4 || board[x+1][y]==5)) cnt++;
        //left up
        if(y-1>=0 && x-1>=0 && (board[x-1][y-1]==1 || board[x-1][y-1]==4 || board[x-1][y-1]==5)) cnt++;
        //left down
        if(y-1>=0 && x+1<xLen && (board[x+1][y-1]==1 || board[x+1][y-1]==4 || board[x+1][y-1]==5)) cnt++;
        //right up
        if(y+1<yLen && x-1>=0 && (board[x-1][y+1]==1 || board[x-1][y+1]==4 || board[x-1][y+1]==5)) cnt++;
        //right down
        if(y+1<yLen && x+1<xLen && (board[x+1][y+1]==1 || board[x+1][y+1]==4 || board[x+1][y+1]==5)) cnt++;

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

    /**
     * When I was working on this problem, I had the following thought.
     * Why for LinkedList and Tree, we only use a node to represent the graph.
     * But in other cases, we use adjacency list and adjacency matrix to represent a graph.
     * Why?
     * This is an extremely important question to think in this question.
     * because by definition the graph is infinite, but we only care about the live nodes.
     * Or say, we only care about some of the sub-graphs.
     * So, the reason why in the case of LinkedList and Tree,
     * we use only a head/root to represent the whole graph,
     * is because the graph is connected and we do care about all the nodes and edges.
     * But more generally, the definition(or the scope) for a graph should be all the nodes and edges of interest.
     * Those nodes may not be connected even.
     * That's why the generic definition of a graph is using adjacency list or adjacency matrix.
     */

    public static class Cell{
        //Cell left, right, up, down, leftUp, leftDown, rightUp, rightDown;
        /**
         * It's better to put all the neighbors into to a list, so we can easily iterate through them.
         */
        Cell[] neighbors = new Cell[8];
        int val;
        Cell(int x){
            this.val = x;
        }
    }

    public void nextState(Set<Cell> board){//better to use Set, so we can avoid duplication
        for(Cell node : board){
            node.val = getNext(node);
            for(Cell neighbor : node.neighbors){
                neighbor.val = getNext(neighbor);
                if(neighbor.val==3) System.out.println("Find");
            }
            /**
             * Without the neighbors_array, I will have write it in the following way, bad design!
             * node.left.val = getNext(node.left);
             * node.right.val = getNext(node.right);
             * node.up.val = getNext(node.up);
             * node.down.val = getNext(node.left);
             * node.leftUp.val = getNext(node.leftUp);
             * node.leftDown.val = getNext(node.leftDown);
             * node.rightUp.val = getNext(node.rightUp);
             * node.rightDown.val = getNext(node.rightDown);
             */
        }
        //There is a big problem here, you cannot iterate through a collection and
        //add elements to the collection at the same time. You can modify or remove the current element only.
        //Otherwise, it will be a current access to the collection.
        //Because accessing the current element is from the iterator, accessing other location will be from a different thread.
        //So we can buf what we want to add to a place and add them all by the end.
        //But even for remove, you cannot use the following "for(Cell node : board)", because it use set.remove() not iterator.remove().
        //Refer to the following link:
        //https://stackoverflow.com/questions/1110404/remove-elements-from-a-hashset-while-iterating

        Set<Cell> buf = new HashSet<>();
        //for(Cell node: board){
        //    if(node.val==4) board.remove(node);
        //    for(Cell neighbor : node.neighbors){
        //        if(neighbor.val==3) buf.add(prepareNode(neighbor));
        //    }
        //}
        Iterator<Cell> it = board.iterator();
        while(it.hasNext()){
            Cell tmp=it.next();
            for(Cell neighbor : tmp.neighbors){
                if(neighbor.val==3) {
                    System.out.println("Adding");
                    buf.add(prepareNode(neighbor));
                }
            }
            if(tmp.val==4) it.remove();
        }
        System.out.println("buf size: "+buf.size());
        board.addAll(buf);
        for(Cell node: board){
            convert(node);
            for(Cell neighbor : node.neighbors){
                convert(neighbor);
            }
        }
    }

    /**
     * 1. If already updated, avoid double update.
     * 2. null case should be considered as 0.
     * 3. assuming node cannot be null, meaning all live nodes' neighbors cannot be null.
     * @param node
     * @return
     */
    private int getNext(Cell node){
        if(node==null) throw new IllegalArgumentException("node cannot be null");
        if(node.val!=0 && node.val!=1) return node.val;
        int result;
        int cnt = 0;
        for(Cell neighbor : node.neighbors){
            if(neighbor!=null && (neighbor.val==1 || neighbor.val==4 || neighbor.val==5)) cnt++;
        }
        if(node.val==1){
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
     * Only convert when node.val is 2, 3, 4, 5
     * @param node
     */
    private void convert(Cell node){
        if(node.val==2 || node.val==4) node.val=0;
        if(node.val==3 || node.val==5) node.val=1;
    }

    private Cell prepareNode(Cell node){
        for(Cell neighbor : node.neighbors){
            if(neighbor==null) neighbor=new Cell(0);
        }
        return node;
    }
}
