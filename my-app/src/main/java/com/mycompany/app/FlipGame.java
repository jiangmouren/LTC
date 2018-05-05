package com.mycompany.app;

/**
 * Created by jiangmouren on 6/3/17.
 */

import java.util.*;

/**
 * Question:
 * You are playing the following Flip Game with your friend:
 * Given a string that contains only these two characters: + and -,
 * you and your friend take turns to flip two consecutive "++" into "--".
 * The game ends when a person can no longer make a move and therefore the other person will be the winner.
 *
 * Write a function to compute all possible states of the string after one valid move.
 *
 * For example, given s = "++++", after one move, it may become one of the following states:
 *
 * [
 *   "--++",
 *   "+--+",
 * "++--"
 * ]
 * If there is no valid move, return an empty list [].
 */

/**
 * Analysis:
 * Every time a player makes a move, it switches a state.
 * We call the original state as State 0 or S0.
 * From S0 to S1, there are many possibilities.
 * For each possible S1, we call it S1(i).
 * So for an arbitrary State, we can denote it as Si(j).
 * For this specific problem, the state will evolve as tree.
 *                    S0
 *             /   /  |  \      \
 *          S1(0)       S1(3)  S1(4)
 *                   /  / \  \
 *
 * When the leaf condition met, is when the game ends.
 *
 * The above the mathematical model for this problem, based on this model there are many ways to ask questions.
 * 1. Given State i, produce State i+1;
 * 2. Construct the whole tree or list out all possibilities(Tree/Graph traversal);
 * 3. Ask if certain player can guarantee a win(Backtracking).
 */

public class FlipGame {
    public List<String> generatePossibleNextMoves(String s) {
        List<String> result = new ArrayList<String>();
        if(s==null || s.length()<2) return result;
        for(int i=0; i<s.length()-1; i++){
            StringBuilder strBuf = new StringBuilder();
            if(s.charAt(i)=='+' && s.charAt(i+1)=='+'){
                char[] sArray = s.toCharArray();
                sArray[i]='-';
                sArray[i+1]='-';
                result.add(new String(sArray));
            }
        }
        return result;
    }
}
