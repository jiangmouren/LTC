package com.mycompany.app;

/**
 * https://leetcode.com/problems/car-fleet-ii/
 * There are n cars traveling at different speeds in the same direction along a one-lane road.
 * You are given an array cars of length n, where cars[i] = [positioni, speedi] represents:
 * positioni is the distance between the ith car and the beginning of the road in meters.
 * It is guaranteed that positioni < positioni+1.
 * speedi is the initial speed of the ith car in meters per second.
 * For simplicity, cars can be considered as points moving along the number line. Two cars collide when they occupy the same position. Once a car collides with another car, they unite and form a single car fleet. The cars in the formed fleet will have the same position and the same speed, which is the initial speed of the slowest car in the fleet.
 * Return an array answer, where answer[i] is the time, in seconds, at which the ith car collides with the next car, or -1 if the car does not collide with the next car. Answers within 10-5 of the actual answers are accepted.
 *
 * Example 1:
 * Input: cars = [[1,2],[2,1],[4,3],[7,2]]
 * Output: [1.00000,-1.00000,3.00000,-1.00000]
 * Explanation: After exactly one second, the first car will collide with the second car, and form a car fleet with speed 1 m/s. After exactly 3 seconds, the third car will collide with the fourth car, and form a car fleet with speed 2 m/s.
 *
 * Example 2:
 * Input: cars = [[3,4],[5,4],[6,3],[9,1]]
 * Output: [2.00000,1.00000,1.50000,-1.00000]
 *
 * Constraints:
 * 1 <= cars.length <= 105
 * 1 <= positioni, speedi <= 106
 * positioni < positioni+1
 */

/**
 * 这是一个augmented jumping pointer问题。
 * 这个题的核心就是：找到i右侧第一个比自己晚collide的车j，那么i的collide时间就是i collide j的时间。
 * 相较于简单jumping pointer的主要不同是这里要比较的量不再是statically available,而是需要Dynamically generated based on i & j.
 */
public class CarFleetII {
    public double[] getCollisionTimes(int[][] cars) {
        int n = cars.length;
        int[] suffix = new int[n];

        //底下这个for loop是典型的jumping pointer的代码，唯一不同的是多了一个check() method
        //因为判断条件不再是static的，而是要dynamically calculated based on ptr的位置。
        for(int i=n-1; i>=0; i--){
            if(i==n-1){
                suffix[i] = n;
                continue;
            }
            int ptr = i+1;
            while(ptr<n && check(cars, suffix, i, ptr)){
                ptr = suffix[ptr];
            }
            suffix[i] = ptr;
        }

        double[] res = new double[n];
        for(int i=0; i<n; i++){
            if(suffix[i]==n){
                res[i] = -1;
            }
            else{
                res[i] = (double)(cars[suffix[i]][0] - cars[i][0]) / (double)(cars[i][1] - cars[suffix[i]][1]);
            }
        }

        return res;
    }

    //return false only when left collides strictly before right collides
    private boolean check(int[][] cars, int[] suffix, int left, int right){
        if(cars[left][1]<=cars[right][1]){
            return true;
        }

        if(suffix[right]==cars.length){
            return false;//right no collides && left_speed>right_speed
        }
        else{
            double timeL = (double)(cars[right][0]-cars[left][0]) / (double)(cars[left][1]-cars[right][1]);
            double timeR = (double)(cars[suffix[right]][0]-cars[right][0]) / (double)(cars[right][1]-cars[suffix[right]][1]);
            return timeL>=timeR;
        }
    }
}
