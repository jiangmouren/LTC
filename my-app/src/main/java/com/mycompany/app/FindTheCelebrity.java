package com.mycompany.app;
import java.util.*;

/**
 * Question: https://leetcode.com/problems/find-the-celebrity/
 * Suppose you are at a party with n people (labeled from 0 to n - 1), and among them, there may exist one celebrity.
 * The definition of a celebrity is that all the other n - 1 people know him/her, but he/she does not know any of them.
 * Now you want to find out who the celebrity is or verify that there is not one.
 * The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information about whether A knows B.
 * You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
 * You are given a helper function bool knows(a, b) which tells you whether A knows B.
 * Implement a function int findCelebrity(n). There will be exactly one celebrity if he/she is in the party.
 * Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.
 *
 * Example 1:
 * Input: graph = [[1,1,0],[0,1,0],[1,1,1]]
 * Output: 1
 * Explanation: There are three persons labeled with 0, 1 and 2. graph[i][j] = 1 means person i knows person j, otherwise graph[i][j] = 0 means person i does not know person j. The celebrity is the person labeled as 1 because both 0 and 2 know him but 1 does not know anybody.
 *
 * Example 2:
 * Input: graph = [[1,0,1],[1,1,0],[0,1,1]]
 * Output: -1
 * Explanation: There is no celebrity.
 *
 * Constraints:
 * n == graph.length
 * n == graph[i].length
 * 2 <= n <= 100
 * graph[i][j] is 0 or 1.
 * graph[i][i] == 1
 *
 * Follow up: If the maximum number of allowed calls to the API knows is 3 * n, could you find a solution without exceeding the maximum number of calls?
 */

/**
 * Analysis:
 * Sliding Window:
 * Use 2 pointers ptr0: Celebrity; ptr1: Interviewee. Move from left to right.
 * If knows(ptr1, ptr0) && !knows(ptr0, ptr1) -> ptr1++
 * else -> ptr0 = ptr1, ptr1++. Because anything in between can't be candidate.
 *
 * By the end, ptr0 is the only candidate, but we have only check ptr0 with what's after it not what's before it
 * Need to do that check before return.
 */

public class FindTheCelebrity{
    public int findCelebrity(int n){
        //ptr0: candidate; ptr1: interviewee
        int ptr0 = 0;
        int ptr1 = 1;//题目说n>=2
        while(ptr1<n){
            if(knows(ptr1, ptr0) && !knows(ptr0, ptr1)){//make sure check both
                ptr1++;
            }
            else{
                ptr0 = ptr1;
                ptr1++;
            }
        }
        //once get out the loop, ptr0 is the only candidate for the celebrity,
        //but we do not know if everyone before ptr0 knows ptr0, and if ptr0 knows them.
        //So we need to check this before make final decision.
        for(int i=0; i<ptr0; i++){
            if(!knows(i, ptr0) || knows(ptr0, i)){
                return -1;
            }
        }
        return ptr0;
    }

    private boolean knows(int a, int b){
        Random random = new Random();
        return random.nextBoolean();
    }
}
