package com.mycompany.app.binarySearch;

/**
 * https://leetcode.com/problems/kth-missing-positive-number/
 * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
 * Find the kth positive integer that is missing from this array.
 *
 * Example 1:
 * Input: arr = [2,3,4,7,11], k = 5
 * Output: 9
 * Explanation: The missing positive integers are [1,5,6,8,9,10,12,13,...]. The 5th missing positive integer is 9.
 *
 * Example 2:
 * Input: arr = [1,2,3,4], k = 2
 * Output: 6
 * Explanation: The missing positive integers are [5,6,7,...]. The 2nd missing positive integer is 6.
 *
 * Constraints:
 * 1 <= arr.length <= 1000
 * 1 <= arr[i] <= 1000
 * 1 <= k <= 1000
 * arr[i] < arr[j] for 1 <= i < j <= arr.length
 */

public class KthMissingPositiveNumber {
    //要点：
    //    1. 对于给定的idx，其左侧missing的正数的的个数为：arr[idx] - (idx + 1)
    //    2. 基于上述关系，就可以进行binary search, 但不同于普通binary search的是要找的数字不在arr当中
    //    3. 要么是arr区间内missing的某个数，要么是区间左侧或者右侧missing的某个数
    public int findKthPositive(int[] arr, int k) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            //不用(left+right)/2 避免(left+right)出现overflow
            //具体在这个题目中因为限定条件不会出现，但是养成这个习惯
            int mid = left + (right - left) / 2;
            //注意这里不能有等号,也就是说如果出现了等号把right降到mid左侧，
            //这样才能保证最后right严格处在要找的位置的左侧, left或者最后处于要找位置的右侧，或者处于要找位置之上
            if (arr[mid] - (mid + 1) < k) {
                left = mid + 1;
            }
            else {// Otherwise, go left.
                right = mid - 1;
            }
        }

        // At the end of the loop, left = right + 1,
        // 这个时候，其实要根据最上面分析的第3点，分3种情况讨论：
        // 如果要找的数在原区间外左侧或者右侧，那么现在也在right的左侧或者left的右侧
        // 如果要找的数在原区间中间，那么现在也在(right, left)当中
        // 1. the kth missing is in-between arr[right] and arr[left]:
        //    arr[right] + (k - (arr[right] - (right + 1))) = k + left
        //    解释：
        //    arr[right] - (right+1)： 是arr[right]左侧missing的正数的个数
        //    k - 左侧missing个数 = k - (arr[right] - (right + 1)): 是还应该往arr[right]右侧找几个数
        //    result = arr[right] + 还应该往arr[right]右侧找几个数 = arr[right] + (k - (arr[right] - (right + 1)))
        //           = arr[right] + k - (arr[right] - (right + 1)) = arr[right] + k - arr[right] + (right + 1)
        //           = k + (right + 1) = k +left
        // 2. the kth missing在区间右侧：同上
        // 3. 在区间左侧： k，刚好也等于left + k,因为这种情况left==0
        return left + k;
    }


    /**
     * Solution 2
     * 一下是我自己想出的BinarySearch解法，基本思路与上面相同，但是实现上不如上解法灵巧。
     * 但好处是比较直观易懂。
     */
    public int findKthPositiveSln2(int[] arr, int k) {
        if(arr.length==1){
            if(k<arr[0]){
                return k;
            }
            else{
                return k - arr[0] + 1 + arr[0];
            }
        }

        int left = 0;
        int right = arr.length - 1;
        int res = left;
        //assumes at least 2 elements
        while(left<right){
            int mid = (left+right)/2;
            int missing = arr[mid] - (mid+1);

            //termination case
            if(left==right-1){
                int rightMissing = arr[right] - (right+1);
                if(k<=missing){
                    res = k;
                    break;
                }
                else if(k>rightMissing){
                    res = k - rightMissing + arr[right];
                    break;
                }
                else{
                    res = k - missing + arr[left];
                    break;
                }
            }

            if(missing<k){//注意这里不能有等号，因为missing count的是左侧missing的number
                left = mid;//take mid not mid+1!!!
            }
            else{
                right = mid;
            }
        }

        return res;
    }
}
