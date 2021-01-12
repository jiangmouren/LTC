package com.mycompany.app;

/**
 * https://leetcode.com/problems/median-of-two-sorted-arrays/
 * Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.
 * Follow up: The overall run time complexity should be O(log (m+n)).
 *
 * Example 1:
 * Input: nums1 = [1,3], nums2 = [2]
 * Output: 2.00000
 * Explanation: merged array = [1,2,3] and median is 2.
 *
 * Example 2:
 * Input: nums1 = [1,2], nums2 = [3,4]
 * Output: 2.50000
 * Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.
 *
 * Example 3:
 * Input: nums1 = [0,0], nums2 = [0,0]
 * Output: 0.00000
 *
 * Example 4:
 * Input: nums1 = [], nums2 = [1]
 * Output: 1.00000
 *
 * Example 5:
 * Input: nums1 = [2], nums2 = []
 * Output: 2.00000
 *
 * Constraints:
 * nums1.length == m
 * nums2.length == n
 * 0 <= m <= 1000
 * 0 <= n <= 1000
 * 1 <= m + n <= 2000
 * -106 <= nums1[i], nums2[i] <= 106
 *
 * Analysis:
 * 这个题还是很有难度的，乍一看我毫无思路，虽然我可以猜到要somehow搞出个binary search出来
 * 但是没有想到两个array上各切一刀，而且这两个的位置还是相关联的。
 * 没想到这个关键点，这个问题也就解不出。
 * 具体详见：https://leetcode.com/problems/median-of-two-sorted-arrays/discuss/2481/Share-my-O(log(min(mn)))-solution-with-explanation
 */

class MedianOfTwoSortedArrays {
    //这里的i&j表达的分别是划在左侧的两个的长度，即左侧最后一个的点分别为i-1 & j-1
    //i+j = m-i+n-j or i+j = m-i+n-j+1
    //if n>=m, we just need to set i=0~m, j=(m+n+1)/2 - i (这个式子，不管m+n是寄还是偶，都成立)
    //B[j-1]<=A[i] && A[i-1]<=B[j]
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //set m, n, nums1, nums2
        int m = nums1.length>nums2.length ? nums2.length : nums1.length;
        int n = nums1.length>nums2.length ? nums1.length : nums2.length;
        if(nums1.length>nums2.length){
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        //handle m==0 case
        if(m==0){
            int idx = (n-1)/2;//no matter n is even or odd, this holds true
            if(n%2==0){
                return (nums2[idx] + nums2[idx+1])/2.0;
            }
            else{
                return nums2[idx];
            }
        }

        //handle Binary Search case
        int iMin = 0;
        int iMax = m;
        double minRight = 0;
        double maxLeft = 0;
        while(iMin<=iMax){
            int i = (iMin+iMax)/2;
            int j = (m+n+1)/2 - i;
            //nums2[j-1]<=nums1[i] && nums2[j]>=nums1[i-1] 可以同时成立，但不可能同时不成立
            if((j==0 || i==m || nums2[j-1]<=nums1[i]) && (i==0 || j==n || nums2[j]>=nums1[i-1])){
                //get minRight
                if(i==m){
                    minRight = nums2[j];
                }
                else if(j==n){
                    minRight = nums1[i];
                }
                else{
                    minRight = Math.min(nums2[j], nums1[i]);
                }

                //get maxLeft
                if(i==0){
                    maxLeft = nums2[j-1];
                }
                else if(j==0){
                    maxLeft = nums1[i-1];
                }
                else{
                    maxLeft = Math.max(nums1[i-1], nums2[j-1]);
                }
                //find result, stop search
                break;
            }
            else if(nums2[j-1]>nums1[i]){
                iMin = i+1;
            }
            else{
                iMax = i-1;
            }
        }
        if((m+n)%2==0){
            return (minRight + maxLeft)/2.0;
        }
        else{
            return maxLeft;
        }
    }
}
