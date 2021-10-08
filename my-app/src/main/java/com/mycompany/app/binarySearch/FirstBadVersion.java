package com.mycompany.app.binarySearch;

/**
 * https://leetcode.com/problems/first-bad-version/
 * You are a product manager and currently leading a team to develop a new product.
 * Unfortunately, the latest version of your product fails the quality check.
 * Since each version is developed based on the previous version,
 * all the versions after a bad version are also bad.
 *
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
 * which causes all the following ones to be bad.
 * You are given an API bool isBadVersion(version) which returns whether version is bad.
 * Implement a function to find the first bad version. You should minimize the number of calls to the API.
 *
 * Example 1:
 * Input: n = 5, bad = 4
 * Output: 4
 * Explanation:
 * call isBadVersion(3) -> false
 * call isBadVersion(5) -> true
 * call isBadVersion(4) -> true
 * Then 4 is the first bad version.
 *
 * Example 2:
 * Input: n = 1, bad = 1
 * Output: 1
 *
 * Constraints:
 * 1 <= bad <= n <= 231 - 1
 */

public class FirstBadVersion{
    public int firstBadVersion(int n) {
        return search(1, (long)n);
    }

    //因为要求Minimize call to "isBadversion", 所以写成recursive的，否则在往左走的时候要多call一次
    private int search(long left, long right){
        //termination
        if(left>right){
            return -1;
        }

        long mid = (left+right)/2;
        if(!isBadVersion((int)mid)){
            return search(mid+1, right);
        }
        else{
            int leftRes = search(left, mid-1);
            return leftRes==-1 ? (int)mid : leftRes;
        }
    }

    //mock function
    private boolean isBadVersion(int version){
        return true;
    }
}