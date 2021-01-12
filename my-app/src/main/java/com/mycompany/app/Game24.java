package com.mycompany.app;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.com/problems/24-game/
 * You have 4 cards each containing a number from 1 to 9.
 * You need to judge whether they could operated through *, /, +, -, (, ) to get the value of 24.
 * Example 1:
 * Input: [4, 1, 8, 7]
 * Output: True
 * Explanation: (8-4) * (7-1) = 24
 * Example 2:
 * Input: [1, 2, 1, 2]
 * Output: False
 * Note:
 * The division operator / represents real division, not integer division. For example, 4 / (1 - 2/3) = 12.
 * Every operation done is between two numbers. In particular, we cannot use - as a unary operator. For example, with [1, 1, 1, 1] as input, the expression -1 - 1 - 1 - 1 is not allowed.
 * You cannot concatenate numbers together. For example, if the input is [1, 2, 1, 2], we cannot write this as 12 + 12.
 */

//因为所有的情况是有限的：A(4,2)*C(4,1)*A(3,2)*C(4,1)*A(2,1)*C(4,1) = 9216
//所以时间和空间复杂度都是O(1)
public class Game24 {
    public boolean judgePoint24(int[] nums) {
        List<Double> numsList = new ArrayList<>();//因为这里的除法不用int division，所以要保持精度，就直接使用double，通常不用float
        for(int num: nums){
            numsList.add((double)num);
        }
        return solve(numsList);
    }

    private boolean solve(List<Double> nums){
        //end condition
        if(nums.size()==1){
            //if(Math.abs(nums.get(0)-24)<1e-6){
            //    System.out.println(nums.get(0));
            //}
            return Math.abs(nums.get(0)-24)<1e-6;
        }

        //recursive case
        List<Double> numsNew = new ArrayList<>();
        for(int i=0; i<nums.size(); i++){
            for(int j=0; j<nums.size(); j++){
                if(i!=j){
                    for(int k=0; k<nums.size(); k++){
                        if(k!=i && k!=j){
                            numsNew.add(nums.get(k));
                        }
                    }

                    for(int k=0; k<4; k++){//0: +, 1: -, 2: *, 3: /
                        //不管是+,-,*其实都存在被算2次的问题
                        //要解决这个问题可以在solve function内部加一个local的hashSet来记录已经出现的pair,key就用x*M+y的方式
                        if(k==0){

                            numsNew.add(nums.get(i)+nums.get(j));
                        }
                        else if(k==1){
                            numsNew.add(nums.get(i)-nums.get(j));
                        }
                        else if(k==2){
                            numsNew.add(nums.get(i)*nums.get(j));
                        }
                        else if(nums.get(j)!=0.0){
                            numsNew.add(nums.get(i)/nums.get(j));//there is no Zero, so no need to worry here
                        }
                        else{//must handle nums.get(j)==0 case, you don't want to solve the numsNew without nums[i] & nums[j]
                            continue;
                        }
                        if(solve(numsNew)){
                            System.out.println(numsNew);
                            return true;
                        }
                        //back-tracking of operators
                        numsNew.remove(numsNew.size()-1);
                    }
                    //back-tracking of first two operands selection
                    numsNew.clear();
                }
            }
        }
        return false;
    }
}
