package com.mycompany.app.math;

import java.util.*;

/**
 * https://leetcode.com/problems/divisor-game/
 * Alice and Bob take turns playing a game, with Alice starting first.
 * Initially, there is a number N on the chalkboard.
 * On each player's turn, that player makes a move consisting of:
 * Choosing any x with 0 < x < N and N % x == 0.
 * Replacing the number N on the chalkboard with N - x.
 * Also, if a player cannot make a move, they lose the game.
 * Return True if and only if Alice wins the game, assuming both players play optimally.
 *
 * Example 1:
 * Input: 2
 * Output: true
 * Explanation: Alice chooses 1, and Bob has no more moves.
 *
 * Example 2:
 * Input: 3
 * Output: false
 * Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.
 *
 * Note:
 * 1 <= N <= 1000
 */

//所有的情况最终都会收敛在1, 2, 3这三种情况里
//主要看所有可能的留给对手的数字里，有没有对方的必输数字，如果有的话那么我必赢
//如果没有，那就是说能给对手的每一个数字都是对手的必赢的数字，这里不是必输，就是必赢，因为双方都是最优玩法
public class DivisorGame {
    public boolean divisorGame(int N) {
        Map<Integer, Boolean> map = new HashMap<>();
        return helper(N, map);
    }

    private boolean helper(int N, Map<Integer, Boolean> map){
        //termination condition
        if(N==1 || N==3){
            return false;
        }
        if(N==2){
            return true;
        }
        if(map.containsKey(N)){
            return map.get(N);
        }
        boolean win = false;
        for(int i=1; i<Math.sqrt(N); i++){
            if(N%i==0 && !helper(N-i, map)){
                win = true;
                break;
            }
        }
        map.put(N, win);
        return win;
    }
}
