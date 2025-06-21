package com.mycompany.app;

/**
 * https://leetcode.com/problems/next-permutation/
 * Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
 * If such an arrangement is not possible, it must rearrange it as the lowest possible order (i.e., sorted in ascending order).
 * The replacement must be in place and use only constant extra memory.
 *
 * Example 1:
 * Input: nums = [1,2,3]
 * Output: [1,3,2]
 *
 * Example 2:
 * Input: nums = [3,2,1]
 * Output: [1,2,3]
 *
 * Example 3:
 * Input: nums = [1,1,5]
 * Output: [1,5,1]
 *
 * Example 4:
 * Input: nums = [1]
 * Output: [1]
 *
 * Constraints:
 * 1 <= nums.length <= 100
 * 0 <= nums[i] <= 100
 */
//解这种题目核心是理解问题的本质
//发现本质的方法无外乎，逻辑推理+play with例子来总结发现规律
//具体就这个题目而言，我们需要找到“一对数”满足以下下条件：
//1. 右边的数大于左边的数
//2. 这对数不一定要挨着
//3. 左边的数要尽可能的往右，低位的变化总是小于高位的变化的
//那么假设这对数是(m,n)，那么说明m<n，n在m的右边，因为m是满足条件的最右边，说明在m的右侧不存在一个数字能在右侧找到比自身大的数
//也就是说m右侧的数，每一个的右侧的所有数都比自身小，说明m右侧根本就是sorted!
//所以找到这对数的过程清楚了：从最右边往左找，m就是第一个不满足递增的数字，然后再回头去找到第一个比m小的数字，那么其有左侧紧挨着的就是n
//也有可能m右侧每一个都比m大，那么最右边的就是n
//找到(m,n)之后就swap这两个数，然后很重要的：把原m位置往右的数给reverse order了。
//因为在swap过之后，m右边的数依然是desending order，要换成asending order，才是“最小的”“更大的”数。
//比如[1,3,2]，如果只是swap 1 and 2, we get [2,3,1], it is obviously larger than [2,1,3]
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        int left = -1;
        //从右往左，expect不减，找到第一个变小的位置，那个位置就是可以去被替换的位置
        for(int i=nums.length-1; i>0; i--){
            if(nums[i]>nums[i-1]){
                left = i-1;
                break;
            }
        }
        //如果整个是sorted，那么就是最大值，就只需要reverse真个数组
        if(left==-1){
            //System.out.println("here");
            reverse(nums, 0, nums.length-1);
            return;
        }
        //用BinarySearch找到右侧最小的比target大的数，swap一下，然后右侧部门依然应该是sorted
        //因为你swap的对象是最小的比target大的数，意味着，那个位置左侧比target大，右侧比target小
        int idx = binarySearch(nums, left+1, nums.length-1, nums[left]);
        swap(nums, left, idx);
        reverse(nums, left+1, nums.length-1);
        return;
    }

    private void reverse(int[] nums, int left, int right){
        while(left<right){//注意这里不能用left！=right,因为如果是偶数，left永远不会等于right
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private void swap(int[] nums, int left, int right){
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }

    //return the smallest element's idx in [left, right] that is larger than target
    private int binarySearch(int[] nums, int left, int right, int target){
        int idx = left;
        while(left<=right){
            if(left==right){
                idx = left;
                break;
            }
            int mid = left + (right - left)/2;
            if(nums[mid]<=target){
                right = mid - 1;
            }
            else{
                if(mid+1<=right && nums[mid+1]>target){
                    left = mid + 1;
                }
                else{
                    idx = mid;
                    break;
                }
            }
        }
        return idx;
    }
}
