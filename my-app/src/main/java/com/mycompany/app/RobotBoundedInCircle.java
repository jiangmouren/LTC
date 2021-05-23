package com.mycompany.app;

/**
 * https://leetcode.com/problems/robot-bounded-in-circle/
 * On an infinite plane, a robot initially stands at (0, 0) and faces north. The robot can receive one of three instructions:
 *
 * "G": go straight 1 unit;
 * "L": turn 90 degrees to the left;
 * "R": turn 90 degrees to the right.
 * The robot performs the instructions given in order, and repeats them forever.
 *
 * Return true if and only if there exists a circle in the plane such that the robot never leaves the circle.
 *
 * Example 1:
 * Input: instructions = "GGLLGG"
 * Output: true
 * Explanation: The robot moves from (0,0) to (0,2), turns 180 degrees, and then returns to (0,0).
 * When repeating these instructions, the robot remains in the circle of radius 2 centered at the origin.
 *
 * Example 2:
 * Input: instructions = "GG"
 * Output: false
 * Explanation: The robot moves north indefinitely.
 *
 * Example 3:
 * Input: instructions = "GL"
 * Output: true
 * Explanation: The robot moves from (0, 0) -> (0, 1) -> (-1, 1) -> (-1, 0) -> (0, 0) -> ...
 *
 *
 * Constraints:
 * 1 <= instructions.length <= 100
 * instructions[i] is 'G', 'L' or, 'R'.
 */
public class RobotBoundedInCircle {
    public boolean isRobotBounded(String instructions) {
        //如果结束的时候是左转或者右转90度，那么一定就是绕圈
        //如果结束的时候，反向180度，那么也一定是绕圈，或者来回震荡
        //如果结束的时候，还是原来的朝向，那么在结束的时候一定不能有位移，否则就会越来越远
        //具体处理的时候要注意角度的normailze，把角度都限定在-180到180当中
        int degree = 0;
        int[] position = {0, 0};
        //(0,1): north; (-1,0): west; (0,-1): south; (1,0): east
        int[] dir = {0, 1};
        for(int i=0; i<instructions.length(); i++){
            if(instructions.charAt(i)=='G'){
                position[0] += dir[0];
                position[1] += dir[1];
            }
            else if(instructions.charAt(i)=='L'){
                degree += 90;
                degree = normalize(degree);
                updateDir(dir, degree);
            }
            else{
                degree -= 90;
                degree = normalize(degree);
                updateDir(dir, degree);
            }
        }
        if(degree==90 || degree==-90 || degree==180){
            return true;
        }
        else{
            return position[0]==0 && position[1]==0;
        }
    }

    //private int normalize(int degree){
    //    if(degree>180){
    //        while(degree>180){
    //            degree -= 360;
    //        }
    //    }
    //    else if(degree<=-180){
    //        while(degree<=-180){
    //            degree += 360;
    //        }
    //    }
    //    return degree;
    //}
    //normalize degree to (-180, 180]
    private int normalize(int degree){
        degree %= 360;//normalize degree to (-360, 360)
        if(degree<=-180){//shift (-360, -180] to [0, 180)
            degree += 360;
        }
        if(degree>180){//shift (180, 360) to (-180, 0)
            degree -= 360;
        }
        return degree;
    }

    //degree: 0, 90, 180, -90
    private void updateDir(int[] dir, int degree){
        switch(degree){
            case 0:
                dir[0] = 0;
                dir[1] = 1;
                break;
            case 90:
                dir[0] = 1;
                dir[1] = 0;
                break;
            case 180:
                dir[0] = 0;
                dir[1] = -1;
                break;
            case -90:
                dir[0] = -1;
                dir[1] = 0;
                break;
        }
    }
}
