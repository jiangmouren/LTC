package com.mycompany.app.companies.tuSimple;
import java.util.*;
/**
 *  给你一堆长方形的长和宽（以List形式输入），再给你一个二维矩阵board，
 *  要你判断，能不能把所有长方形不重叠的放在这个board里，注意board里面有空穴，空穴处不能放置长方形
 */

/**
 * 整体思路是backtracking，只不过这当中要拿到所有的选项这一步比较麻烦，需要用到一个二维的DP
 * 然后再backtracking的时候还要考虑到rectangles可以旋转的情况。
 */
public class PackingRectangles {
    //rectangles[0]: width; rectangles[1] : length; board[i][j] 1 means hole, 0 means not hole.
    //each entry represents a 1X1 square.
    public boolean pack(int[][] rectangles, int[][] board){
        return packHelper(rectangles, 0, board);
    }

    private boolean packHelper(int[][] rects, int pos, int[][] board){
        //termination case
        if(pos>=rects.length){
            return true;
        }

        int[] rect = rects[pos];
        List<int[]> choices = getChoices(rect, board);
        for(int[] choice : choices){
            updateBoard(rect, choice, board, true);
            if(packHelper(rects, pos+1, board)){
                return true;
            }
            updateBoard(rect, choice, board, false);
        }
        //rotate rect
        int[] roRect = {rect[1], rect[0]};
        List<int[]> roChoices = getChoices(roRect, board);
        for(int[] roChoice : roChoices){
            updateBoard(roRect, roChoice, board, true);
            if(packHelper(rects, pos+1, board)){
                return true;
            }
            updateBoard(roRect, roChoice, board, false);
        }
        return false;
    }

    private List<int[]> getChoices(int[] rect, int[][] board){
        int width = rect[0];
        int height = rect[1];
        if(width>board[0].length || height>board.length){
            return new ArrayList<>();
        }
        //(i, j)对齐rect的右下角
        int[][] dpW = new int[board.length][board[0].length];
        int[][] dpH = new int[board.length][board[0].length];
        for(int i=0; i<dpW.length; i++){
            for(int j=0; j<dpW[0].length; j++){
                if(board[i][j]==1){
                    dpW[i][j] = 0;
                }
                else{
                    dpW[i][j] = 1;
                    if(j-1>=0){
                        dpW[i][j] += dpW[i][j-1];
                    }
                }
            }
        }

        for(int i=0; i<dpH.length; i++){
            for(int j=width-1; j<dpH[0].length; j++){
                if(dpW[i][j]<width){
                    dpH[i][j] = 0;
                }
                else{
                    dpH[i][j] = 1;
                    if(i-1>=0){
                        dpH[i][j] += dpH[i-1][j];
                    }
                }
            }
        }

        List<int[]> res = new ArrayList<>();
        for(int i=height-1; i<dpH.length; i++){
            for(int j=width-1; j<dpH[0].length; j++){
                if(dpH[i][j]>=height){
                    int[] pos = {i, j};
                    res.add(pos);
                }
            }
        }
        return res;
    }

    private void updateBoard(int[] rect, int[] pos, int[][] board, boolean fill){
        int val = fill ? 1 : 0;
        for(int i=pos[0]; i>pos[0]-rect[1]; i--){
            for(int j=pos[1]; j>pos[1]-rect[0]; j--){
                board[i][j] = val;
            }
        }
    }

    public static void main(String[] args){
        PackingRectangles instance = new PackingRectangles();
        int[][] rectangles0 = {{3, 1}, {3, 1}, {2, 2}};//true
        int[][] rectangles1 = {{3, 1}, {4, 1}, {2, 2}};//false
        int[][] board = {{0, 1, 0, 0}, {0, 0, 0, 0}, {0, 0, 1, 0}, {1, 0, 0, 0}};
        boolean res0 = instance.pack(rectangles0, board);
        boolean res1 = instance.pack(rectangles1, board);
        System.out.println(res0);
        System.out.println(res1);
    }
}
