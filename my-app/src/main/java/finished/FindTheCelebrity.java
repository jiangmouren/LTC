package finished;
/**
 * Question:
 * Suppose you are at a party with n people (labeled from 0 to n - 1) and among them, there may exist one celebrity.
 * The definition of a celebrity is that all the other n - 1 people know him/her but he/she does not know any of them.
 * Now you want to find out who the celebrity is or verify that there is not one.
 * The only thing you are allowed to do is to ask questions like: "Hi, A. Do you know B?" to get information of whether A knows B.
 * You need to find out the celebrity (or verify there is not one) by asking as few questions as possible (in the asymptotic sense).
 * You are given a helper function bool knows(a, b) which tells you whether A knows B.
 * Implement a function int findCelebrity(n), your function should minimize the number of calls to knows.
 * Note: There will be exactly one celebrity if he/she is in the party.
 * Return the celebrity's label if there is a celebrity in the party. If there is no celebrity, return -1.
 */

/**
 * Analysis:
 * This problem is very much like the ShortestWordDistance problem and the MajorityElement problem.
 * They are similar in the sense that not all pairs need to be compared. We are all looking for "biased" pair.
 * Specifically, for this one, we can use 2 pointers(Celebrity ptr + Interviewee ptr) move from left to right.
 * If Interviewee ptr knows Celebrity ptr, move Interviewee ptr is not Celebrity for sure, Interviewee ptr++
 * If Interviewee ptr does not know Celebrity ptr, Celebrity ptr = Interviewee ptr.
 * We did not check if anything in between previous Celebrity ptr and Interviewee ptr,
 * because they are guaranteed not to be celebrity.
 *
 */
import java.util.*;

public class FindTheCelebrity{
    /**
     * @param a
     * @param b
     * @return returns true if a knows b.
     */
    private boolean knows(int a, int b){
        Random random = new Random();
        return random.nextBoolean();
    }
    public int find(int n){
        if(n<2) throw new IllegalArgumentException("Input should be larger than 1");
        int ptr1=0, ptr2=1;
        while(ptr2<n){
            if(knows(ptr2, ptr1) && !knows(ptr1, ptr2)){// this is a trap, need to check both.
                ptr2++;
            }
            else{
                ptr1=ptr2;
                ptr2++;
            }
        }
        //once get out the loop, ptr1 is the only candidate for the celebrity,
        //but we do not know if everyone before ptr1 knows ptr1, and if ptr1 knows them.
        //So we need to check this before make final decision.
        for(int i=0; i<ptr1; i++){
            if(!knows(i, ptr1) || knows(ptr1, i)) return -1;
        }
        return ptr1;
    }

}
