package com.mycompany.app;

/**
 * https://leetcode.com/problems/find-the-smallest-divisor-given-a-threshold/
 * Given an array of integers nums and an integer threshold, we will choose a positive integer divisor,
 * divide all the array by it, and sum the division's result.
 * Find the smallest divisor such that the result mentioned above is less than or equal to threshold.
 * Each result of the division is rounded to the nearest integer greater than or equal to that element.
 * (For example: 7/3 = 3 and 10/2 = 5).
 * It is guaranteed that there will be an answer.
 *
 * Example 1:
 * Input: nums = [1,2,5,9], threshold = 6
 * Output: 5
 * Explanation: We can get a sum to 17 (1+2+5+9) if the divisor is 1.
 * If the divisor is 4 we can get a sum of 7 (1+1+2+3) and if the divisor is 5 the sum will be 5 (1+1+1+2).
 *
 * Example 2:
 * Input: nums = [44,22,33,11,1], threshold = 5
 * Output: 44
 *
 * Example 3:
 * Input: nums = [21212,10101,12121], threshold = 1000000
 * Output: 1
 *
 * Example 4:
 * Input: nums = [2,3,5,7,11], threshold = 11
 * Output: 3
 *
 * Constraints:
 * 1 <= nums.length <= 5 * 104
 * 1 <= nums[i] <= 106
 * nums.length <= threshold <= 106
 */
public class FindTheSmallestDivisorGivenAThreshold {
    public int smallestDivisor(int[] nums, int threshold) {
        //用最大值为上限，因为 nums.length <= threshold <= 10^6 表达的就是每个都去最大值，那么每项都是1，因为threshold大于长度，所以就一定是一个解
        //然后就在1到上限之间做 Binary Search，最多尝试lg(n)次每次要把每项都算出来需要O(n)，所以一共需要O(nlgn)
        int max = 0;
        int total = 0;
        for(int num : nums){
            total += num;
            max = Math.max(max, num);
        }
        int left = total%threshold==0 ? total/threshold : total/threshold+1;
        int right = max;
        int res = 1;
        while(left<=right){
            int mid = (left+right)/2;
            int sum = getSum(nums, mid);
            //下面这段删掉的比较有意思，就算找到刚好等于threshold的了也不一定就找到了最小divisor
            //因为sum的除法存在较大误差，比如 200/34 + 100/34 + 14/34，按照getSum的算法是10
            //而 200/37 + 100/37 + 14/37，按照getSum算法，结果也是10
            //所以要把“等于”跟“大于”归为一类。
            //if(sum==threshold){
            //    System.out.println("1");
            //    res = mid;
            //    break;
            //}
            if(sum>threshold){
                left = mid + 1;
            }
            else{
                if(mid-1>=left && getSum(nums, mid-1)<=threshold){
                    right = mid - 1;
                }
                else{
                    res = mid;
                    break;
                }
            }
        }
        return res;
    }

    private int getSum(int[] nums, int divisor){
        int sum = 0;
        for(int num : nums){
            sum += num/divisor;
            if(num%divisor>0){
                sum += 1;
            }
        }
        return sum;
    }
}
