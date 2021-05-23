package com.mycompany.app;

/**
 * https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/
 * There are several cards arranged in a row, and each card has an associated number of points The points are given in the integer array cardPoints.
 * In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k cards.
 * Your score is the sum of the points of the cards you have taken.
 * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
 *
 * Example 1:
 * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
 * Output: 12
 * Explanation: After the first step, your score will always be 1.
 * However, choosing the rightmost card first will maximize your total score.
 * The optimal strategy is to take the three cards on the right, giving a final score of 1 + 6 + 5 = 12.
 *
 * Example 2:
 * Input: cardPoints = [2,2,2], k = 2
 * Output: 4
 * Explanation: Regardless of which two cards you take, your score will always be 4.
 *
 * Example 3:
 * Input: cardPoints = [9,7,7,9,7,7,9], k = 7
 * Output: 55
 * Explanation: You have to take all the cards. Your score is the sum of points of all cards.
 *
 * Example 4:
 * Input: cardPoints = [1,1000,1], k = 1
 * Output: 1
 * Explanation: You cannot take the card in the middle. Your best score is 1.
 *
 * Example 5:
 * Input: cardPoints = [1,79,80,1,1,1,200,1], k = 3
 * Output: 202
 *
 * Constraints:
 * 1 <= cardPoints.length <= 10^5
 * 1 <= cardPoints[i] <= 10^4
 * 1 <= k <= cardPoints.length
 */
public class MaximumPointsYouCanObtainFromCards {
    //只能从前或者从后面连续去，所以对于k的总长度，可以考虑前面取了i,后面取了j，i+j=k
    //所以问题就是在所有的可能的i,j的组合中找出其中的最大值，然后需要一个Prefix sum 和一个suffix Sum
    public int maxScore(int[] cardPoints, int k) {
        int[] prefix = new int[k];
        int[] suffix = new int[k];
        int temp = 0;
        for(int i=0; i<k; i++){
            temp += cardPoints[i];
            prefix[i] = temp;
        }
        temp = 0;
        for(int i=cardPoints.length-1; i>cardPoints.length-1-k; i--){
            temp += cardPoints[i];
            suffix[cardPoints.length-1-i] = temp;
        }
        int max = Integer.MIN_VALUE;
        for(int i=-1; i<k; i++){//注意这里要从i=-1开始算起，对应是前面一个都不取得情况
            int point = 0;
            if(i>=0){
                point += prefix[i];
            }
            int j = k-1-i;//j is length
            if(j>0){
                point += suffix[j-1];
            }
            max = Math.max(max, point);
        }
        return max;
    }
}
