package com.mycompany.app;
import java.util.*;

/**
 * https://leetcode.com/problems/car-fleet/
 * N cars are going to the same destination along a one lane road.
 * The destination is target miles away.
 * Each car i has a constant speed speed[i] (in miles per hour), and initial position position[i] miles towards the target along the road.
 * A car can never pass another car ahead of it, but it can catch up to it, and drive bumper to bumper at the same speed.
 * The distance between these two cars is ignored - they are assumed to have the same position.
 * A car fleet is some non-empty set of cars driving at the same position and same speed.  Note that a single car is also a car fleet.
 * If a car catches up to a car fleet right at the destination point, it will still be considered as one car fleet.
 * How many car fleets will arrive at the destination?
 *
 * Example 1:
 * Input: target = 12, position = [10,8,0,5,3], speed = [2,4,1,1,3]
 * Output: 3
 * Explanation:
 * The cars starting at 10 and 8 become a fleet, meeting each other at 12.
 * The car starting at 0 doesn't catch up to any other car, so it is a fleet by itself.
 * The cars starting at 5 and 3 become a fleet, meeting each other at 6.
 * Note that no other cars meet these fleets before the destination, so the answer is 3.
 *
 * Note:
 * 0 <= N <= 10 ^ 4
 * 0 < target <= 10 ^ 6
 * 0 < speed[i] <= 10 ^ 6
 * 0 <= position[i] < target
 * All initial positions are different.
 */

public class CarFleet {
    //先对position speed pair根据Position sort
    //然后对每辆车（不考虑被前车阻挡的情况下），计算到达target的时间
    //然后的逻辑就是如果i到达target的时间小于等于右边i+k的的一辆车，说明'i'就会被'i+k' block住
    //从右往左数有多少个没有被block住的车，就有多少个fleet经过target
    public int carFleet(int target, int[] position, int[] speed) {
        int n = position.length;
        int[][] cars = new int[n][2];
        for(int i=0; i<n; i++){
            cars[i][0] = position[i];
            cars[i][1] = speed[i];
        }
        Arrays.sort(cars, (a, b)->a[0]-b[0]);

        double[] time = new double[n];
        for(int i=0; i<n; i++){
            int distance = target - cars[i][0];
            double duration = (double)distance / (double)cars[i][1];
            time[i] = duration;
        }

        int cnt = 0;
        double max_pre = 0;
        for(int i=n-1; i>=0; i--){
            if(i==n-1){
                cnt++;
            }
            else if(time[i]>max_pre){
                cnt++;
            }
            max_pre = Math.max(max_pre, time[i]);
        }
        return cnt;
    }
}
