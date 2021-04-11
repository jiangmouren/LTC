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
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        //这是一个变向的binary search:找median的本质就是找到每个array在分到median左侧的位置
        //let i & j分别表示nums1 & nums2分到median左边的长度，那么i & j的位置需要满足一下条件：
        //i+j=m-i+n-j, if m+n is even; i+j=m-i+n-j+1, if m+n is odd and the mid is assigned to the left side.
        //j=(m+n)/2 - i, if m+n is even; j = (m+n+1)/2 - i, if m+n is odd;而且后面的式子对于m+n为偶数的时候依然成立，因为round down
        //所以给定一个i的位置，j的位置也就定了，我们需要做的就是找到一个符合一下条件的i的位置：
        //A[i]>=B[j-1] && B[j]>=A[i-1], 而且注意，因为A,B都是sorted,以上两个不等式可以同时成立，但是不能同时不成立
        //因为A,B都是sorted,可以根据上述不等式，针对i在A内做Binary Search
        //注意这里用i来表达j, j = (m+n+1)/2 - i. i的区间是[0, m], n必须大于等于m，否则j会出现负数，所以第一步是先两个数组中确定m, n(找到长的那个)

        //确定m, n
        int m = nums1.length;
        int n = nums2.length;
        if(m>n){
            int temp = n;
            n = m;
            m = temp;
            int[] arr = nums1;
            nums1 = nums2;
            nums2 = arr;
        }

        //需要特殊处理m==0的情况，因为如果m==0,下面i==m & j==n会同时出现，就破坏了下面binary search的假设
        if(m==0){
            int idx = (n-1)/2;
            if(n%2==0){
                return (nums2[idx]+nums2[idx+1])/2.0;//注意这里一定要写成2.0，否则参与运算的全是int,最后return的还是int，而且会做round down
            }
            else{
                return nums2[idx];
            }
        }

        //binary Search
        int iMin = 0;
        int iMax = m;
        int leftMax = 0;
        int rightMin = 0;
        while(iMin<=iMax){
            int i = (iMin+iMax)/2;
            int j = (m+n+1)/2 -i ;
            //下面的特殊情况首先可以简单的从边界条件的角度发现，其次有具体意涵：前两种特殊情况都对应i一路向右到头；后两种特殊情况都对应i一路向左到头
            if((i==m || j==0 || nums1[i]>=nums2[j-1]) && (j==n || i==0 || nums2[j]>=nums1[i-1])){
                if(i==m){
                    rightMin = nums2[j];
                }
                else if(j==n){
                    rightMin = nums1[i];
                }
                else{
                    rightMin = Math.min(nums1[i], nums2[j]);
                }

                if(i==0){
                    leftMax = nums2[j-1];
                }
                else if(j==0){
                    leftMax = nums1[i-1];
                }
                else{
                    leftMax = Math.max(nums1[i-1], nums2[j-1]);
                }
                break;
            }
            else if(nums1[i]<nums2[j-1]){
                iMin = i + 1;
            }
            else{
                iMax = i - 1;
            }
        }
        if((m+n)%2==0){
            return (leftMax+rightMin)/2.0;//注意这里一定要写成2.0
        }
        else{
            return leftMax;
        }
    }
}
